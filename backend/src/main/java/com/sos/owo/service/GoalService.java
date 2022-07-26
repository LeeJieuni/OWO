package com.sos.owo.service;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Goal;
import com.sos.owo.domain.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    @Transactional
    public void saveGoal(int memberId,Goal goal){
//        goal.setMember(memberId);
        goalRepository.saveGoal(memberId,goal);
    }

    @Transactional
    public void updateGoal(int memberId,Goal goal){
        goalRepository.updateGoal(memberId,goal);
    }

    @Transactional
    public void deleteGoal(int memberId, int goal_id){
        goalRepository.deleteGoal(memberId,goal_id);
    }

    @Transactional
    public List<Goal> findGoal(int memberId){
        return goalRepository.findGoal(memberId);
    }
}
