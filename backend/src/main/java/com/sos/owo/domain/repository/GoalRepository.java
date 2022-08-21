package com.sos.owo.domain.repository;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Goal;
import com.sos.owo.domain.Member;
import com.sos.owo.dto.GoalResponseDto;
import com.sos.owo.error.Exception.custom.SomethingNotFoundException;
import com.sos.owo.error.Exception.custom.SomethingNullException;
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
public class GoalRepository{
    @PersistenceContext
    private final EntityManager em;



    public void saveGoal(int memberId,Goal goal){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("memberId:"+memberId);

        goal.setMember(findMember);
        findMember.getGoalList().add(goal);
//        System.out.println(findMember.getGoalList());
        em.persist(findMember);
    }

    public void updateGoal(int memberId,Goal goal){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        List<Goal> goalList = findMember.getGoalList();
        if(!goalList.isEmpty()) {
            for (Goal g : goalList) {
                System.out.println(g.getExercise());
                if (g.getExercise() == goal.getExercise()) {
                    g.setHour(goal.getHour());
                    break;

                }
            }
        }else{
            throw new SomethingNullException("goalList");
        }
    }


    //goal_id로 실질적으로 db에서 삭제되고, memberId는 ..? -> 추후 코드 수정해야함
    public int deleteGoal(int memberId, int goalId){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        Goal goal = em.find(Goal.class,goalId);
        if(goal == null) throw new SomethingNullException("goal(id:"+goalId+")");

        return em.createQuery("delete from Goal as g where g.id = :goalId and g.member.id = :memberId")
                .setParameter("goalId",goalId).setParameter("memberId",memberId).executeUpdate();
    }

    public List<GoalResponseDto> findGoal(int memberId){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        List<Goal> list = findMember.getGoalList();
        List<GoalResponseDto> responseList = new ArrayList<>();
        if(!list.isEmpty()) {
            for (Goal item : list) {
                responseList.add(new GoalResponseDto(item.getId(), item.getExercise().name(), item.getMember().getId(), item.getHour()));
            }
        }
        return responseList;
    }
}
