package com.sos.owo.service;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Goal;
import com.sos.owo.domain.repository.GoalRepository;
import com.sos.owo.dto.GoalResponseDto;
import com.sos.owo.error.Exception.custom.SomethingNotFoundException;
import com.sos.owo.error.Exception.custom.SomethingNullException;
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
    public void saveGoal(int memberId,Goal goal)throws SomethingNotFoundException{
//        goal.setMember(memberId);
        goalRepository.saveGoal(memberId,goal);
    }

    @Transactional
    public void updateGoal(int memberId,Goal goal) throws SomethingNullException ,SomethingNotFoundException{
        goalRepository.updateGoal(memberId,goal);
    }

    @Transactional
    public int deleteGoal(int memberId, int goalId)throws SomethingNotFoundException{
        return goalRepository.deleteGoal(memberId,goalId);
    }

    @Transactional
    public List<GoalResponseDto> findGoal(int memberId) throws SomethingNotFoundException {
        return goalRepository.findGoal(memberId);
    }
}
