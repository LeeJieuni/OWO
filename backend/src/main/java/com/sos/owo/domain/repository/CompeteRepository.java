package com.sos.owo.domain.repository;

import com.sos.owo.domain.Compete;
import com.sos.owo.domain.Member;
import com.sos.owo.error.Exception.custom.SomethingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompeteRepository {

    @PersistenceContext
    private final EntityManager em;
    private final MemberRepository memberRepository;


    //경쟁모드의 사용자 최고기록 조회
    public List<Integer> findBestScore(int memberId){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        Query findCompete = em.createQuery("select c from Compete c join c.member m" +
                                " where m.id = :memberId "
                        , Compete.class)
                .setParameter("memberId", memberId);

        List<Compete> bestScore = findCompete.getResultList();

        int first = 0, second = 0, third = 0;
        for(Compete c : bestScore){
            first = c.getCompeteScore1();
            second = c.getCompeteScore2();
            third = c.getCompeteScore3();
        }

        List<Integer> result = new ArrayList<>();
        result.add(first);
        result.add(second);
        result.add(third);

        return result;
    }

    //경쟁모드의 사용자 최고기록 조회
    public Compete checkBestScore(int memberId){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        Query findCompete = em.createQuery("select c from Compete c join c.member m" +
                        " where m.id = :memberId "
                , Compete.class)
                .setParameter("memberId", memberId);

        List<Compete> bestScore = findCompete.getResultList();

       if(bestScore.size() == 0 || bestScore == null || bestScore.get(0) == null){
           return null;
       }
        return bestScore.get(0);
    }




    //경쟁모드의 사용자 최고기록 조회
    public void saveBestScore(int memberId, int score1, int score2, int score3){
        Compete compete = new Compete();
        compete.setMember(memberRepository.findOne(memberId));
        compete.setCompeteScore1(score1);
        compete.setCompeteScore2(score2);
        compete.setCompeteScore3(score3);
        em.persist(compete);
        return;
    }


}
