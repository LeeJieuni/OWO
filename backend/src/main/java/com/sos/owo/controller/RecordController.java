package com.sos.owo.controller;

import com.sos.owo.domain.MD5Generator;
import com.sos.owo.domain.Record;
import com.sos.owo.domain.Tag;
import com.sos.owo.dto.*;
import com.sos.owo.service.RecordImgService;
import com.sos.owo.service.RecordService;
import com.sos.owo.service.TagService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecordController {

    private final RecordImgService recordImgService;
    private final RecordService recordService;
    private final TagService tagService;

    @ApiOperation(value = "운동 종료 후 기록 저장",notes = "운동 종료 후 기록한 정보를 저장한다.")
    @PostMapping("/api/record/finish/{memberId}/{meetingRoomId}")
    public ResponseEntity<?> registerRecord(@PathVariable("memberId") int memberId, @PathVariable("meetingRoomId") int meetingRoomId, @RequestBody RecordDto recordDto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            Record record = recordService.registRecord(memberId,meetingRoomId,recordDto.toEntity());
            int recordId = record.getRecordId();
            List<String> tagList = recordDto.getTagList();
            tagService.registTag(recordId,tagList);

            message.setStatus(StatusEnum.OK);
            message.setMessage("운동기록 저장 성공. data에 null로 표현될텐데 잘 들어갔음음");
           return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("운동기록 저장 실패");
            return new ResponseEntity<String>("OVERLAP", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생(ex.값이 잘 안들어가거나 sql문이 제대로 실행되지 않는 경우)");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "운동 단일 기록 조회",notes = "운동 기록 한개의 정보를 불러온다.")
    @GetMapping("/api/record/{recordId}")
    public ResponseEntity<?> findRecord(@PathVariable("recordId") int recordId){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {

            RecordResponseDto recordResponseDto = recordService.findRecord(recordId);

            message.setStatus(StatusEnum.OK);
            message.setMessage("운동기록 조회 성공");
            message.setData(recordResponseDto);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("운동기록 조회 실패");
            return new ResponseEntity<String>("OVERLAP", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생(ex.값이 잘 안들어가거나 sql문이 제대로 실행되지 않는 경우)");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 운동기록에 맞는 태그 리스트 조회",notes = "기록id에 따른(하나의 기록) 태그 이름들 리스트 조회")
    @GetMapping("/api/record/tag/list/{recordId}")
    public ResponseEntity<?> findTagList(@PathVariable("recordId") int recordId){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            List<TagResponseDto> tagResponseList = tagService.findTagList(recordId);

            message.setStatus(StatusEnum.OK);
            message.setMessage("태그리스트 조회 성공");
            message.setData(tagResponseList);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("태그리스트 조회 실패");
            return new ResponseEntity<String>("OVERLAP", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생(ex.값이 잘 안들어가거나 sql문이 제대로 실행되지 않는 경우)");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "태그 아이디로 단일 태그 조회",notes = "태그id에 따른 태그 정보 조회")
    @GetMapping("/api/record/tag/{tagId}")
    public ResponseEntity<?> findTag(@PathVariable("tagId") int tagId){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            TagResponseDto tag = tagService.findTagOne(tagId);

            message.setStatus(StatusEnum.OK);
            message.setMessage("단일 태그 조회 성공");
            message.setData(tag);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("단일 태그 조회 실패");
            return new ResponseEntity<String>("OVERLAP", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생(ex.값이 잘 안들어가거나 sql문이 제대로 실행되지 않는 경우)");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 날짜에 맞는 운동 리스트 조회",notes = "사용자의 특정 날짜에 해당하는 운동 기록 리스트를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId",value = "사용자 id",dataType = "int",paramType = "path"),
            @ApiImplicitParam(name = "date",value = "운동한 날짜 (형식 : 20220805)",dataType = "LocalDate",paramType = "path")
    })
    @GetMapping("/api/record/day/{memberId}/{date}")
    public ResponseEntity<?> findRecordByDay(@PathVariable("memberId") int memberId,@PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") @Parameter(schema = @Schema(type="string" ,format = "date", example = "20220805")) LocalDate dateTime){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            System.out.println(dateTime);
            List<RecordResponseDto> recordResponseDtoList = recordService.findRecordByDay(memberId,dateTime);

            message.setStatus(StatusEnum.OK);
            message.setMessage("특정일 운동 리스트 조회 성공");
            message.setData(recordResponseDtoList);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("특정일 운동 리스트 조회 실패");
            return new ResponseEntity<String>("OVERLAP", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생(ex.값이 잘 안들어가거나 sql문이 제대로 실행되지 않는 경우)");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
