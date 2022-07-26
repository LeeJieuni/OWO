package com.sos.owo.domain.repository;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Goal;
import com.sos.owo.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class GoalRepository {

    @PersistenceContext
    private final EntityManager em;



    public void saveGoal(int memberId,Goal goal){
        Member findMember = em.find(Member.class,memberId);
        goal.setMember(findMember);
        findMember.getGoalList().add(goal);
//        System.out.println(findMember.getGoalList());
        em.persist(findMember);
    }

    public void updateGoal(int memberId,Goal goal){
        Member findMember = em.find(Member.class,memberId);
        List<Goal> goalList = findMember.getGoalList();

        for (Goal g:goalList) {
            System.out.println(g.getExercise());
            if(g.getExercise() == goal.getExercise()){
                g.setHour(goal.getHour());
                break;

            }
        }
    }


    public void deleteGoal(int memberId, int goal_id){
        Member findMember = em.find(Member.class,memberId);
        Goal findGoal = em.find(Goal.class,goal_id);
        findMember.getGoalList().remove(findGoal);
        findGoal.setMember(null);
        em.remove(findGoal);
        em.flush();

    }


}
