package com.sos.owo.controller;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Goal;
import com.sos.owo.dto.GoalSaveRequestDto;
import com.sos.owo.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;
    //운동 목표 추가
    @PostMapping("/goal/{memberId}") // 추후 api/user  추가하기
    public ResponseEntity<?> saveGoal(@PathVariable("memberId") int memberId, @RequestBody GoalSaveRequestDto goalSaveRequestDto){
        try {
            goalService.saveGoal(memberId,goalSaveRequestDto.toEntity());

            return new ResponseEntity<String>("SUCCESS SAVE GOAL", HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //운동 목표 수정 -- 수정 필요
    @PutMapping("goal/{memberId}") //api/user/
    public ResponseEntity<?> updateGoal(@PathVariable("memberId") int memberId, @RequestBody GoalSaveRequestDto goalSaveRequestDto){
        try { //@PathVariable("goalId") int goalId,
            goalService.updateGoal(memberId,goalSaveRequestDto.toEntity());

            return new ResponseEntity<String>("SUCCESS UPDATE GOAL", HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //운동 목표 삭제

//    @DeleteMapping("goal/{memberId}/{exercise}") //api/user/
    @DeleteMapping("goal/{memberId}/{goal_id}") //api/user/
    public ResponseEntity<?> deleteGoal(@PathVariable("memberId") int memberId, @PathVariable("goal_id")int goal_id){
//        System.out.println(exercise);
        try {
            goalService.deleteGoal(memberId,goal_id);

            return new ResponseEntity<String>("SUCCESS DELETE GOAL", HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("goal/{memberId}")
    public ResponseEntity<?> findGoal(@PathVariable("memberId") int memberId){
        try {
//            goalService.findGoal(memberId);

            return new ResponseEntity<String>("SUCCESS DELETE GOAL", HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
