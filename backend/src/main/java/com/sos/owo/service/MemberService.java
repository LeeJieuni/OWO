package com.sos.owo.service;

import com.sos.owo.config.security.JwtTokenProvider;
import com.sos.owo.domain.Gender;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.repository.CompeteRepository;
import com.sos.owo.domain.repository.MemberRepository;
import com.sos.owo.domain.repository.MemberRepository2;
import com.sos.owo.dto.MemberBodyDto;
import com.sos.owo.dto.MemberLoginResponseDto;
import com.sos.owo.dto.MemberSloganDto;
import com.sos.owo.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
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
    private final MemberRepository2 memberRepository2;

    private final CompeteRepository competeRepository;

    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public int join(Member member){
        checkEmailDuplicate(member.getEmail()); // 중복 회원 검증
        member.setEnable(false);
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
        memberRepository.savePoint(point, id);
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
        MemberLoginResponseDto memberDto = MemberLoginResponseDto.builder()
                        .email(email)
                        .accessToken(jwtTokenProvider.createToken(email, member.getRoles()))
                        .refreshToken(member.getRefreshToken())
                        .id(member.getId()).nick(member.getNick())
                        .gender(member.getGender()).age(member.getAge())
                        .height(member.getHeight()).weight(member.getWeight())
                        .activityNum(member.getActivityNum()).activityHour(member.getActivityHour())
                        .activityLevel(member.getActivityLevel())
                        .build();

        return memberDto;
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

        MemberLoginResponseDto memberDto = MemberLoginResponseDto.builder()
                .email(member.getEmail())
                .accessToken(jwtTokenProvider.createToken(member.getEmail(), member.getRoles()))
                .refreshToken(member.getRefreshToken())
                .id(member.getId()).nick(member.getNick())
                .gender(member.getGender()).age(member.getAge())
                .height(member.getHeight()).weight(member.getWeight())
                .activityNum(member.getActivityNum()).activityHour(member.getActivityHour())
                .activityLevel(member.getActivityLevel())
                .build();

        return memberDto;
    }

    @Transactional
    public void logoutMember(String token) throws IllegalStateException {
        boolean result = jwtTokenProvider.validateToken(token);
        if(!result) throw new IllegalStateException("토큰 만료 되었습니다.");
        Member member = memberRepository.findByEmail(jwtTokenProvider.getUserPk(token));
        member.changeRefreshToken("invalidate");
    }

    @Transactional
    public void updateMember(MemberUpdateDto memberUpdateDto){
        memberRepository.updateMember(memberUpdateDto);
    }

    @Transactional
    public void updateMemberSlogan(MemberSloganDto memberSloganDto){
        memberRepository.updateMemberSlogan(memberSloganDto);
    }

    @Transactional
    public MemberSloganDto getMemberSlogan(int memberId){
        return memberRepository.getMemberSlogan(memberId);
    }

    @Transactional
    public double getPointPercentage(int memberid) throws Exception {
        int rank = memberRepository2.findRanking(memberid);
        int allCnt = memberRepository2.findMemberCnt();
        double percentage = ((double) rank / allCnt) * 100;
        return percentage;
    }

    @Transactional
    public int getMemberPoint(int memberId){
        Member findMember = memberRepository.findOne(memberId);
        return findMember.getPoint();
    }

    @Transactional
    public MemberBodyDto getMemberBodyInformation(int memberId) throws IllegalStateException {
        Member findMember = memberRepository.findOne(memberId);
        MemberBodyDto memberBodyDto = new MemberBodyDto();
        if(findMember.getHeight() == 0 || findMember.getWeight() == 0 || findMember.getAge() == 0 || findMember.getActivityLevel() == 0 || findMember.getGender() == null){
            throw new IllegalStateException("신체 정보가 입력되지 않았습니다.");
        }

        double bmi = findMember.getWeight() / (findMember.getHeight() * 0.01) / (findMember.getHeight() * 0.01);
        memberBodyDto.setBmi(bmi);
        double bmr = 0.0;
        double caloriePerDay = 0.0;
        if(findMember.getGender().equals(Gender.MALE)){
            bmr = 66.47 + (13.75*findMember.getWeight()) + (5*findMember.getHeight()) - (6.76*findMember.getAge());
        } else {
            bmr = 665.1 + (9.05*findMember.getWeight()) + (1.85*findMember.getHeight()) - (4.68*findMember.getAge());
        }
        memberBodyDto.setBmr(Math.round(bmr));
        if(findMember.getActivityLevel() == 1){
           caloriePerDay = bmr * 1.2;
        } else if(findMember.getActivityLevel() == 2){
            caloriePerDay = bmr * 1.375;
        } else if(findMember.getActivityLevel() == 3){
            caloriePerDay = bmr * 1.55;
        } else if(findMember.getActivityLevel() == 4){
            caloriePerDay = bmr * 1.725;
        } else {
            caloriePerDay = bmr * 1.9;
        }
        memberBodyDto.setCaloriePerDay(caloriePerDay);
        return memberBodyDto;
    }

    @Transactional
    public MemberLoginResponseDto getMember(String accessToken) throws Exception {
        String email = jwtTokenProvider.getUserPk(accessToken);
        Member member = memberRepository.findByEmail(email);

        // 리프레쉬 토큰 발급
        MemberLoginResponseDto memberDto = MemberLoginResponseDto.builder()
                .email(email)
                .accessToken(accessToken)
                .refreshToken(member.getRefreshToken())
                .id(member.getId()).nick(member.getNick())
                .gender(member.getGender()).age(member.getAge())
                .height(member.getHeight()).weight(member.getWeight())
                .activityNum(member.getActivityNum()).activityHour(member.getActivityHour())
                .activityLevel(member.getActivityLevel())
                .build();

        return memberDto;
    }

    @Transactional
    public void joinSocial(UserDto user){
        Member member = new Member();
        member.setEmail(user.getEmail());
        member.setNick(user.getName());
        member.setPw("social");
        member.setEnable(true);
        memberRepository.save(member);
    }

    @Transactional
    public void socialLogin(String email, String refreshToken){
        memberRepository.socialLogin(email, refreshToken);
    }


}
