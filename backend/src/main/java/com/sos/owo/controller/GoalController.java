package com.sos.owo.controller;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Goal;
import com.sos.owo.dto.GoalResponseDto;
import com.sos.owo.dto.GoalSaveRequestDto;
import com.sos.owo.dto.Message;
import com.sos.owo.dto.StatusEnum;
import com.sos.owo.service.GoalService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    //운동 목표 추가
    @ApiOperation(value = "운동 목표 추가",notes = "memberId와 목표운동, 목표시간을 받아서 추가한다.")
    @ApiImplicitParam(name = "memberId",value = "사용자 id",dataType = "int",paramType = "path")
//    @ApiResponses({@ApiResponse(code = 200,message = "운동 목표 추가 성공"),
//            @ApiResponse(code = 400,message = "잘못된 요청"),
//            @ApiResponse(code = 403,message = "금지됨"),
//            @ApiResponse(code = 500,message = "내부 서버 에러___")
//    })
    @PostMapping("/api/user/goal/{memberId}")
    public ResponseEntity<?> saveGoal(@PathVariable("memberId") int memberId, @RequestBody GoalSaveRequestDto goalSaveRequestDto){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        try {
            goalService.saveGoal(memberId,goalSaveRequestDto.toEntity());
            message.setStatus(StatusEnum.OK);
            message.setMessage("운동 목표 추가 성공");
            message.setData(goalSaveRequestDto);
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.OK);
//            return new ResponseEntity<String>("SUCCESS INSERT GOAL",HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("잘못된 요청(ex. memberId가 null인 경우)");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<String>("OVERLAP", HttpStatus.BAD_REQUEST);g

        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("내부 서버 에러");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    //운동 목표 수정
    @ApiOperation(value = "운동 목표 수정",notes = "memberId와 목표운동, 목표시간을 받아서 수정한다.")
    @ApiImplicitParam(name = "memberId",value = "사용자 id",dataType = "int",paramType = "path")
    @PutMapping("/api/user/goal/{memberId}")
    public ResponseEntity<?> updateGoal(@PathVariable("memberId") int memberId, @RequestBody GoalSaveRequestDto goalSaveRequestDto){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        try {
            goalService.updateGoal(memberId,goalSaveRequestDto.toEntity());
            message.setStatus(StatusEnum.OK);
            message.setMessage("운동 목표 수정 성공");
            message.setData(goalSaveRequestDto);
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.OK);
//            return new ResponseEntity<String>("SUCCESS UPDATE GOAL", HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("잘못된 요청(ex. memberId가 null인 경우)");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("내부 서버 에러");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //운동 목표 삭제
    @ApiOperation(value = "운동 목표 삭제",notes = "memberId와 목표운동 id를 받아서 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId",value = "사용자 id",dataType = "int",paramType = "path"),
            @ApiImplicitParam(name = "goalId",value = "사용자의 운동 목표 id",dataType = "int",paramType = "path")
    })
    @DeleteMapping("/api/user/goal/{memberId}/{goalId}")
    public ResponseEntity<?> deleteGoal(@PathVariable("memberId") int memberId, @PathVariable("goalId")int goalId){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        try {
            goalService.deleteGoal(memberId,goalId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("운동 목표 삭제 성공");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.OK);
//            return new ResponseEntity<String>("SUCCESS DELETE GOAL", HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("잘못된 요청(ex. memberId/goalId가 null인 경우)");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("내부 서버 에러");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //운동 목표 리스트 조회
    @ApiOperation(value = "운동 목표 리스트 조회",notes = "memberId를 받아서 그 사람의 운동 목표 리스트를 조회한다.")
    @ApiImplicitParam(name = "memberId",value = "사용자 id",dataType = "int",paramType = "path")
    @GetMapping("/api/user/goal/{memberId}")
    public ResponseEntity<?> findGoal(@PathVariable("memberId") int memberId){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        try {
            List<GoalResponseDto> goalList = goalService.findGoal(memberId);
//            model.addAttribute("goalList",goalList);
            message.setStatus(StatusEnum.OK);
            message.setMessage("운동 목표 리스트 조회 성공");
            message.setData(goalList);
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.OK);
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

    //운동 주간 목표 시간 조회
    @ApiOperation(value = "운동 주간 목표 시간 조회",notes = "memberId를 받아서 그 사람의 운동 목표 리스트를 조회하여 시간을 더한 주의 목표 시간을 조회한다.")
    @ApiImplicitParam(name = "memberId",value = "사용자 id",dataType = "int",paramType = "path")
    @GetMapping("/api/user/weekGoal/{memberId}")
    public ResponseEntity<?> findWeekGoal(@PathVariable("memberId") int memberId){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        try {
            List<GoalResponseDto> goalList = goalService.findGoal(memberId);
            int weekGoalHour = 0;
            for (int i=0;i<goalList.size();i++){
                weekGoalHour += goalList.get(i).getHour();
            }
//            model.addAttribute("weekGoal",weekGoalHour);
            message.setStatus(StatusEnum.OK);
            message.setMessage("운동 주간 목표 시간 조회 성공");
            message.setData(weekGoalHour);
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.OK);
//            return new ResponseEntity<String>("SUCCESS GET WEEKLY GOAL HOUR", HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("잘못된 요청(ex. memberId가 null인 경우)");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("내부 서버 에러");
            return new ResponseEntity<>(message,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
