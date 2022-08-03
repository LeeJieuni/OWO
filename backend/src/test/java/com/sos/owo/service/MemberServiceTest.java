package com.sos.owo.service;

import com.sos.owo.domain.Compete;
import com.sos.owo.domain.Gender;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.repository.CompeteRepository;
import com.sos.owo.dto.MemberLoginResponseDto;
import com.sos.owo.dto.MemberRequestLoginDto;
import com.sos.owo.dto.MemberSaveRequestDto;
import com.sos.owo.dto.MemberUpdateDto;
import org.assertj.core.api.Assertions;
import com.sos.owo.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.fail;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional

public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CompeteRepository competeRepository;


    @Test
    public void 회원가입() throws Exception {
        // Given
        Member member = new Member();
        member.setEmail("1234@naver.com");
        member.setPw("1234");
        System.out.println("===================");

        // When
        int savedId = memberService.join(member);
        System.out.println(savedId);
        // Then
        Assertions.assertThat(member.getId()).isEqualTo(savedId);

    }
    @Test
    public void 중복_회원_예외() throws Exception {
        // Given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setEmail("1234@naver.com");
        member1.setPw("1234");

        member2.setEmail("1234@naver.com");
        member2.setPw("1234");

        // When
        memberService.join(member1);
        memberService.join(member2);

        // Then
        fail("예외가 발생해야 한다.");
    }

    @Test
    public void 최고기록을_불러온다() throws Exception {

        //given
        Member member = new Member();
        member.setId(1);
        member.setEmail("1234@naver.com");
        member.setPw("1234");

        Compete compete = new Compete();
        compete.setCompeteId(1);
        compete.setMember(member);
        compete.setCompeteScore1(60);
        compete.setCompeteScore2(70);
        compete.setCompeteScore3(80);

        //when
        List<Integer> check = memberService.findBestScore(member.getId());
        System.out.println(check);
    }

    @Test
    public void 로그인() throws Exception {
        // given
        Member member = new Member();
        member.setEmail("1234@naver.com");
        member.setPw("1234");

        memberService.join(member);

        MemberRequestLoginDto requestLoginDto = new MemberRequestLoginDto();
        requestLoginDto.setEmail("1234@naver.com");
        requestLoginDto.setPassword("1234");


        // when
        MemberLoginResponseDto responseDto =
                memberService.login(requestLoginDto.getEmail(), requestLoginDto.getPassword());

        // then
        Member findMember = memberRepository.findByEmail(responseDto.getEmail());
        Assertions.assertThat(responseDto.getRefreshToken()).isEqualTo(findMember.getRefreshToken());

    }

    @Test
    public void 로그아웃() throws Exception {
        // given
        Member member = new Member();
        member.setEmail("1234@naver.com");
        member.setPw("1234");

        memberService.join(member);

        MemberRequestLoginDto requestLoginDto = new MemberRequestLoginDto();
        requestLoginDto.setEmail("1234@naver.com");
        requestLoginDto.setPassword("1234");

        MemberLoginResponseDto responseDto =
                memberService.login(requestLoginDto.getEmail(), requestLoginDto.getPassword());

        // when
        memberService.logoutMember(responseDto.getRefreshToken());

        // then
        Member findMember = memberRepository.findByEmail(responseDto.getEmail());
        Assertions.assertThat("invalidate").isEqualTo(findMember.getRefreshToken());

    }

//    @Test
//    @Rollback(value = false)
//    public void 비밃번호변경() throws Exception {
//        // given
//        Member member = new Member();
//        member.setEmail("1111@naver.com");
//        member.setPw("1234");
//
//        memberService.join(member);
//
//        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
//                .email("1111@naver.com")
//                .password("1111")
//                .build();
//
//
//        // when
//        memberService.updatePassword(requestDto.getEmail(), requestDto.getPassword());
//
//        // then
//        Member findMember = memberRepository.findByEmail(member.getEmail());
//
//
//        Assertions.assertThat(requestDto.getPassword()).isEqualTo(findMember.getPassword());
//
//    }

    @Test
    public void 회원정보수정() throws Exception {
        // given
        Member member = new Member();
        member.setEmail("1234@naver.com");
        member.setPw("1234");

        memberService.join(member);

        MemberUpdateDto updateDto = MemberUpdateDto.builder()
                .email(member.getEmail())
                .id(member.getId())
                .nick("testNick")
                .gender(Gender.MALE)
                .age(20)
                .height(170)
                .weight(50)
                .activityNum(1)
                .activityHour(3)
                .activityLevel(3)
                .build();


        // when
        memberService.updateMember(updateDto);

        // then
        Member findMember = memberRepository.findByEmail(updateDto.getEmail());
        Assertions.assertThat(updateDto.getNick()).isEqualTo(findMember.getNick());
        Assertions.assertThat(updateDto.getAge()).isEqualTo(findMember.getAge());
        Assertions.assertThat(updateDto.getHeight()).isEqualTo(findMember.getHeight());
        Assertions.assertThat(updateDto.getActivityNum()).isEqualTo(findMember.getActivityNum());
        Assertions.assertThat(updateDto.getActivityHour()).isEqualTo(findMember.getActivityHour());
        Assertions.assertThat(updateDto.getWeight()).isEqualTo(findMember.getWeight());
        Assertions.assertThat(updateDto.getActivityLevel()).isEqualTo(findMember.getActivityLevel());
    }



}
