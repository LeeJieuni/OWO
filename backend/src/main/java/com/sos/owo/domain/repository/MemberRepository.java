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
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Member member){
        member.setEnable(false);
        em.persist(member);
    }

    public Member findOne(int id){
        System.out.println(id);
        Member findMember = em.find(Member.class, id);
        System.out.println(findMember.getEmail());
        return findMember;
    }

    public boolean existsByEmail(String email){
        List<Member> memberList = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        if(memberList.size() == 0) return false;
        return true;
    }

    //자유,영상모드 경험치 저장
    public void saveExp(int exp, int id){
        Member findMember = em.find(Member.class, id);
        int current = findMember.getExp();
        findMember.setPoint(current+exp);
    }

    public void setVerified(Member member){
        member.setEnable(true);
        em.persist(member);
        em.flush();
    }




}
