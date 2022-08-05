package com.sos.owo.domain.repository;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Goal;
import com.sos.owo.domain.Member;
import com.sos.owo.dto.GoalResponseDto;
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


    //goal_id로 실질적으로 db에서 삭제되고, memberId는 ..? -> 추후 코드 수정해야함
    public int deleteGoal(int memberId, int goalId){
        return em.createQuery("delete from Goal as g where g.id = :goalId and g.member.id = :memberId")
                .setParameter("goalId",goalId).setParameter("memberId",memberId).executeUpdate();

//        Member findMember = em.find(Member.class,memberId);
//        Goal findGoal = em.find(Goal.class,goalId);
//        findMember.getGoalList().remove(findGoal);
//        findGoal.setMember(null);
//        em.remove(findGoal);
//        em.persist(findMember);
//        em.flush();

    }

    public List<GoalResponseDto> findGoal(int memberId){
        Member findMember = em.find(Member.class,memberId);
        List<Goal> list = findMember.getGoalList();
        List<GoalResponseDto> responseList = new ArrayList<>();
        for (Goal item:list) {
            responseList.add(new GoalResponseDto(item.getId(),item.getExercise().name(),item.getMember().getId(),item.getHour()));
        }

        return responseList;
    }
}
