package com.sos.owo.service;

import com.sos.owo.config.security.JwtTokenProvider;
import com.sos.owo.domain.Goal;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.repository.CompeteRepository;
import com.sos.owo.domain.repository.MemberRepository;
import com.sos.owo.domain.repository.MemberRepository2;
import com.sos.owo.dto.MemberLoginResponseDto;
import com.sos.owo.dto.MemberResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final CompeteRepository competeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public int join(Member member){
        checkEmailDuplicate(member.getEmail()); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public boolean checkEnable(String email) throws IllegalStateException {
        Member findMember = memberRepository.findByEmail(email);
        if(findMember.isEnable()) return true;
        return false;
    }

    @Transactional
    public void checkEmailDuplicate(String email) {
        boolean userEmailDuplicate = memberRepository.existsByEmail(email);
        if(userEmailDuplicate) throw new IllegalStateException("이미 존재하는 회원입니다.");

    }

    @Transactional
    public Member checkEmail(String email){
        System.out.println(email);
        boolean userEmailDuplicate = memberRepository.existsByEmail(email);
        if(!userEmailDuplicate) throw new IllegalStateException("해당 이메일에 존재하는 회원이 없습니다.");

        Member member = memberRepository.findByEmail(email);
        return member;
    }

    @Transactional
    public void updatePassword(String email, String password){
        memberRepository.updatePassword(email, password);
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

    @Transactional
    public MemberLoginResponseDto login(String email, String password) throws Exception {
        Member member = memberRepository.findByEmail(email);
        if(!member.getPw().equals(password)){
            throw new IllegalArgumentException(("잘못된 비밀번호 입니다."));
        }

        // 리프레쉬 토큰 발급
        member.changeRefreshToken(jwtTokenProvider.createRefreshToken(email, member.getRoles()));
        return new MemberLoginResponseDto(email, jwtTokenProvider.createToken(email, member.getRoles()), member.getRefreshToken());
    }

    @Transactional
    public MemberLoginResponseDto refreshToken(String token, String refreshToken) throws Exception {

        //if(memberRepository.isLogout(jwtTokenProvider.getUserPk(token))) throw new AccessDeniedException("");
        // 아직 만료되지 않은 토큰으로는 refresh 할 수 없음
        if(jwtTokenProvider.validateToken(token)) throw new AccessDeniedException("token이 만료되지 않음");

        Member member = memberRepository.findByEmail(jwtTokenProvider.getUserPk(refreshToken));
        System.out.println(member.getRefreshToken());
        if(!refreshToken.equals(member.getRefreshToken())) throw new AccessDeniedException("해당 멤버가 존재하지 않습니다.");

        if(!jwtTokenProvider.validateToken(member.getRefreshToken()))
            throw new IllegalStateException("다시 로그인 해주세요.");

        member.changeRefreshToken(jwtTokenProvider.createRefreshToken(member.getEmail(), member.getRoles()));
        return new MemberLoginResponseDto(member.getEmail(), jwtTokenProvider.createToken(member.getEmail(), member.getRoles()), member.getRefreshToken());
    }

    @Transactional
    public void logoutMember(String token) throws IllegalStateException {
        boolean result = jwtTokenProvider.validateToken(token);
        if(!result) throw new IllegalStateException("토큰 만료 되었습니다.");
        Member member = memberRepository.findByEmail(jwtTokenProvider.getUserPk(token));
        member.changeRefreshToken("invalidate");
    }


}
