package com.sos.owo.service;

import com.sos.owo.domain.Member;
import com.sos.owo.domain.repository.CompeteRepository;
import com.sos.owo.domain.repository.MemberRepository;
import com.sos.owo.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final CompeteRepository competeRepository;

    // 회원가입
    @Transactional
    public int join(Member member){
        checkEmailDuplicate(member.getEmail()); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public MemberResponseDto findOne(int memberId){
        Member findMember = memberRepository.findOne(memberId);
        MemberResponseDto responseDto = new MemberResponseDto();
        responseDto.setId(findMember.getId());
        responseDto.setEmail(findMember.getEmail());
        responseDto.setPw(findMember.getPw());
        responseDto.setAge(findMember.getAge());
        if(findMember.getGender() != null)
            responseDto.setGender(findMember.getGender().toString());
        responseDto.setHeight(findMember.getHeight());
        responseDto.setWeight(findMember.getWeight());
        responseDto.setActivityNum(findMember.getActivityNum());
        responseDto.setActivityHour(findMember.getActivityHour());

        return responseDto;
    }

    @Transactional
    public void checkEmailDuplicate(String email) {
        boolean userEmailDuplicate = memberRepository.existsByEmail(email);
        if(userEmailDuplicate) throw new IllegalStateException("이미 존재하는 회원입니다.");

    }

    @Transactional
    public List<Integer> findBestScore(int memberId){
        return competeRepository.findBestScore(memberId);
    }

    @Transactional
    public void savePoint(int point, int id){
        competeRepository.savePoint(point, id);
    }

    @Transactional
    public void saveExp(int exp, int id){
        memberRepository.saveExp(exp, id);
    }

}
