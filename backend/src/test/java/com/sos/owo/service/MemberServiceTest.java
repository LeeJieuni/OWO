package com.sos.owo.service;

import com.sos.owo.domain.Member;
import org.assertj.core.api.Assertions;
import com.sos.owo.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.fail;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional

public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


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


}
