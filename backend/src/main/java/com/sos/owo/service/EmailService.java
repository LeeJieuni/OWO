package com.sos.owo.service;

import com.sos.owo.domain.EmailToken;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class EmailService {
    private final EmailTokenService emailTokenService;
    private final MemberRepository memberRepository;

    // 이메일 토큰을 검증하는 메소드
    public boolean verifyEmail(String token) {
        // 이메일 토큰을 찾아옴
        EmailToken findEmailToken = emailTokenService.findByIdAndExpirationDateAfterAndExpired(token);

        // 이메일 성공 인증 로직 구현 부분
        // 유저의 인증 내용 변경하는 방법

        // 토큰의 유저 ID를 통해 유저 인증 정보를 가져움
        Member findMember =  memberRepository.findOne(findEmailToken.getMemberId());

        if(findMember == null) {
            return false;
        } else {
            memberRepository.setVerified(findMember);
            return true;
        }


    }


}
