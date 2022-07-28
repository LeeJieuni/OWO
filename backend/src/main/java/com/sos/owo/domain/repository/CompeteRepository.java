package com.sos.owo.domain.repository;

import com.sos.owo.domain.Compete;
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


    //경쟁모드의 사용자 최고기록 조회
    public List<Integer> findBestScore(int memberId){
        Query findCompete = em.createQuery("select c from Compete c join c.member m" +
                                " where m.id = :memberId "
                        , Compete.class)
                .setParameter("memberId", memberId);

        List<Object[]> bestScore = findCompete.getResultList();

        int first = 0, second = 0, third = 0;
        for(Object[] row : bestScore){
            first = (Integer)row[1];
            second = (Integer)row[2];
            third = (Integer)row[3];
        }

        List<Integer> result = new ArrayList<>();
        result.add(first);
        result.add(second);
        result.add(third);

        return result;
    }





}
