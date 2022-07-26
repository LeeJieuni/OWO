package com.sos.owo.controller;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Goal;
import com.sos.owo.dto.GoalSaveRequestDto;
import com.sos.owo.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    //운동 목표 추가
    @PostMapping("/api/user/goal/{memberId}")
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

    //운동 목표 수정
    @PutMapping("/api/user/goal/{memberId}")
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
    @DeleteMapping("/api/user/goal/{memberId}/{goal_id}")
    public ResponseEntity<?> deleteGoal(@PathVariable("memberId") int memberId, @PathVariable("goal_id")int goal_id){
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

    //운동 목표 리스트 조회
    @GetMapping("/api/user/goal/{memberId}")
    public ResponseEntity<?> findGoal(@PathVariable("memberId") int memberId, Model model){
        try {
            List<Goal> goalList = goalService.findGoal(memberId);
            model.addAttribute("goalList",goalList);
            return new ResponseEntity<String>("SUCCESS GET GOAL LIST", HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //운동 주간 목표 시간 조회
    @GetMapping("/api/user/weekGoal/{memberId}")
    public ResponseEntity<?> findWeekGoal(@PathVariable("memberId") int memberId, Model model){
        try {
            List<Goal> goalList = goalService.findGoal(memberId);
            int weekGoalHour = 0;
            for (int i=0;i<goalList.size();i++){
                weekGoalHour += goalList.get(i).getHour();
            }
            model.addAttribute("weekGoal",weekGoalHour);
            return new ResponseEntity<String>("SUCCESS GET WEEKLY GOAL HOUR", HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
