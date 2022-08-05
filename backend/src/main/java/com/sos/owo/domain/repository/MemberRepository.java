package com.sos.owo.domain.repository;

import com.sos.owo.domain.Member;
import com.sos.owo.dto.MemberSloganDto;
import com.sos.owo.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Member member){
        member.setJoindate(LocalDateTime.now());
        member.setRoles(Collections.singletonList("ROLE_USER")); // 최초 가입 시 USER로 설정
        em.persist(member);
    }

    public Member findOne(int id){
        Member findMember = em.find(Member.class, id);
        return findMember;
    }

    public boolean existsByEmail(String email){
        List<Member> memberList = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        if(memberList.size() == 0) return false;
        return true;
    }

    public boolean isLogout(String email){
        Member findMember = findByEmail(email);
        if(findMember.getRefreshToken().equals("invalid")) return true;
        return false;
    }

    //경쟁모드 경험치 저장
    public void savePoint(int point, int id){
        Member findMember = findOne(id);
        int current = findMember.getPoint() + point;
        findMember.setPoint(current);
        em.persist(findMember);
        em.flush();
    }

    //자유,영상모드 경험치 저장
    public void saveExp(int exp, int id){
        Member findMember = findOne(id);
        int current = findMember.getExp() + exp;
        findMember.setPoint(current);
        em.persist(findMember);
        em.flush();
    }

    public void setVerified(Member member){
        member.setEnable(true);
        em.persist(member);
        em.flush();
    }

    public Member findByEmail(String email) throws IllegalStateException {
        List<Member> memberList = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        if(memberList.size() == 0) throw new IllegalStateException("해당 이메일을 가진 사용자가 없습니다.");
        return memberList.get(0);
    }


    public void updatePassword(String email, String password) {
        Member findMember = findByEmail(email);
        findMember.updatePassword(password);
    }

    public void updateMember(MemberUpdateDto memberUpdateDto){
        Member findMember = findByEmail(memberUpdateDto.getEmail());
        findMember.updateMember(memberUpdateDto);
    }

    public void updateMemberSlogan(MemberSloganDto memberSloganDto){
        Member findMember = findOne(memberSloganDto.getId());
        findMember.updateMemberSlogan(memberSloganDto.getSlogan());
    }

    public MemberSloganDto getMemberSlogan(int memberId){
        Member findMember = findOne(memberId);
        return new MemberSloganDto(memberId, findMember.getSlogan());
    }

    public void socialLogin(String email, String refresh){
        Member member = findByEmail(email);
        member.changeRefreshToken(refresh);
    }


}
