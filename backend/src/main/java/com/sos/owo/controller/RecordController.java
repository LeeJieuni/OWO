package com.sos.owo.controller;

import com.sos.owo.domain.MD5Generator;
import com.sos.owo.domain.Record;
import com.sos.owo.dto.*;
import com.sos.owo.service.RecordImgService;
import com.sos.owo.service.RecordService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class RecordController {

    private final RecordImgService recordImgService;
    private final RecordService recordService;

    @ApiOperation(value = "운동 종료 후 기록 저장",notes = "운동 종료 후 기록한 정보를 저장한다.")
    @PostMapping("/api/record/finish/{memberId}/{meetingRoomId}")
    public ResponseEntity<?> registerRecord(@PathVariable("memberId") int memberId, @PathVariable("meetingRoomId") int meetingRoomId, @RequestBody RecordDto recordDto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            recordService.registRecord(memberId,meetingRoomId,recordDto.toEntity());
            message.setStatus(StatusEnum.OK);
            message.setMessage("운동기록 저장 성공");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("저장 실패");
            return new ResponseEntity<String>("OVERLAP", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
