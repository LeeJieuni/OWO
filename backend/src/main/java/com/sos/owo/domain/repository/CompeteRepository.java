package com.sos.owo.domain.repository;

import com.sos.owo.domain.Compete;
import com.sos.owo.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompeteRepository {

    @PersistenceContext
    private final EntityManager em;


    //경쟁모드의 사용자 최고기록 조회
    public List<Integer> findBestScore(int memberId){
        //Compete findCompete = em.find(Compete.class, id);
        Compete findCompete = em.createQuery("select c from Compete c where c.member_id = :member_id ", Compete.class)
                .setParameter("memberId", memberId)
                .getSingleResult();


        int first = findCompete.getCompeteScore1();
        int second = findCompete.getCompeteScore2();
        int third = findCompete.getCompeteScore3();

        List<Integer> bestScore = new ArrayList<>();
        bestScore.add(first);
        bestScore.add(second);
        bestScore.add(third);

        return bestScore;
    }

    //경쟁모드 경험치 저장
    public void savePoint(int point, int id){
        Member findMember = em.find(Member.class, id);
        int current = findMember.getPoint();
        findMember.setPoint(current+point);
    }




}
