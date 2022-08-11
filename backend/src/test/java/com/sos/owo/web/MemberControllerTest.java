package com.sos.owo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sos.owo.domain.Compete;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.repository.MemberRepository;
import com.sos.owo.dto.MemberResponseDto;
import com.sos.owo.dto.MemberSaveRequestDto;
import com.sos.owo.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @PersistenceContext
    EntityManager em;

    @Test
    public void 회원_가입한다() throws Exception {
        // given
        String email = "1234@naver.com";
        String password = "1234";
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        String url = "http://localhost:" + port + "/api/auth/join";

        // when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        // then
//         all = MemberRepository.findOne();
//        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(title);
//        Assertions.assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    public void 목표_추가한다() throws Exception{

    }


}