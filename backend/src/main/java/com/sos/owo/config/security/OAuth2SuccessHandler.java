package com.sos.owo.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.repository.MemberRepository;
import com.sos.owo.service.MemberService;
import com.sos.owo.service.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.io.IOException;
import java.util.ArrayList;

// Success Handler 진입 -> 로그인이 완료된 상태
@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserDto user = userRequestMapper.toDto(oAuth2User);

        // 최초 로그인한 회원이라면 회원가입 처리를 한다.
        if(!memberRepository.existsByEmail(user.getEmail())){
            memberService.joinSocial(user);
        }


        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        String accessToken = jwtTokenProvider.createToken(user.getEmail(), roles);
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), roles);

       // 로그인 처리
        memberService.socialLogin(user.getEmail(), refreshToken);

        log.info("email : {}", user.getEmail());
        log.info("name : {}", user.getName());
        log.info("access token : {}", accessToken);
        log.info("refresh token : {}", refreshToken);
        String targetUrl;

        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("accessToken", accessToken);
        response.setContentType("application/json;charset=UTF-8");
        targetUrl = UriComponentsBuilder.fromHttpUrl("https://i7c202.p.ssafy.io:3443/oauth/redirect")
        .queryParam("accessToken", accessToken)
        .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


}
