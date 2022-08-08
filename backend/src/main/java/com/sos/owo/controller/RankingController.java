package com.sos.owo.controller;

import com.sos.owo.dto.GoalSaveRequestDto;
import com.sos.owo.dto.Message;
import com.sos.owo.dto.ResponseRankingDto;
import com.sos.owo.dto.StatusEnum;
import com.sos.owo.service.MyRankDto;
import com.sos.owo.service.RankService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RankingController {

    private final RankService rankService;

    @ApiOperation(value = "랭킹 조회(1~3위)",notes = "랭킹(1위~3위)의 리스트를 불러온다.")
    @GetMapping("/api/rankingList")
    public ResponseEntity<?> getRankingList(){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        try {
            List<ResponseRankingDto> rankingDtoList=  rankService.getRankingList();
            message.setStatus(StatusEnum.OK);
            message.setMessage("랭킹 리스트 불러오기 성공");
            message.setData(rankingDtoList);
            return new ResponseEntity<>(message,httpHeaders, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("잘못된 요청(ex. memberId가 null인 경우)");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("내부 서버 에러");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "나의 랭킹 순위 조회",notes = "나의 memberId를 통해 나의 랭킹정보를 조회한다.")
    @GetMapping("/api/ranking/{memberId}")
    @ApiImplicitParam(name = "memberId",value = "사용자 id",dataType = "int",paramType = "path")
    public ResponseEntity<?> getMyRank(@PathVariable("memberId") int memberId){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        try {
            int rank = rankService.getMyRank(memberId);
            MyRankDto responseDto = new MyRankDto();
            responseDto.setRanking(rank);
            message.setStatus(StatusEnum.OK);
            message.setMessage("나의 랭킹 조회 성공");
            message.setData(responseDto);
            return new ResponseEntity<>(message,httpHeaders, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("잘못된 요청(ex. memberId가 null인 경우)");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("내부 서버 에러");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "랭킹 리셋관련 api(크론탭사용)",notes = "어제의 운동왕을 조회하는 부분입니다.")
    @GetMapping("/api/rank/reset")
    public ResponseEntity<?> reset(){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        try {
            rankService.addRank();
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공");
            return new ResponseEntity<>(message,httpHeaders, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("잘못된 요청");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("내부 서버 에러");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
