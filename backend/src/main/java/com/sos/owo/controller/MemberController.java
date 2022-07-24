package com.sos.owo.controller;

import com.sos.owo.config.security.JwtTokenProvider;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.repository.MemberRepository2;
import com.sos.owo.dto.MemberLoginResponseDto;
import com.sos.owo.dto.MemberSaveRequestDto;
import com.sos.owo.service.EmailTokenService;
import com.sos.owo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailTokenService emailTokenService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;




    @PostMapping("/api/auth/join")
    public ResponseEntity<?> signUp(@RequestBody MemberSaveRequestDto requestDto){
        try {
            int id = memberService.join(requestDto.toEntity());
            String emailTokenId = emailTokenService.createEmailToken(id, requestDto.getEmail());
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/api/user/compete/{memberId}")
    public ResponseEntity<?> findBestScore(@PathVariable("memberId") int memberId, Model model){
        try {
            List<Integer> bestScore = memberService.findBestScore(memberId);
            model.addAttribute("bestScores", bestScore);
            return new ResponseEntity<List<Integer>>( memberService.findBestScore(memberId), HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/api/user/point/{point}/{memberId}")
    public ResponseEntity<?> savePoint(@PathVariable("point") int point, @PathVariable("memberId") int memberId){
        try {
            memberService.savePoint(point, memberId);
            return new ResponseEntity<String>("경쟁모드 경험치 저장성공", HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/user/point/{exp}/{memberId}")
    public ResponseEntity<?> saveExp(@PathVariable("exp") int exp, @PathVariable("memberId") int memberId){
        try {
            memberService.saveExp(exp, memberId);
            return new ResponseEntity<String>("자유/영상모드 경험치 저장성공", HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/auth/password")
    public ResponseEntity<?> findPassword(@RequestParam String email){
        try {
            Member member = memberService.checkEmail(email);
            String emailTokenId= emailTokenService.createPasswordEmailToken(member.getId(), email);
            return new ResponseEntity<String>(emailTokenId, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("NO EMAIL", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/api/auth/password")
    public ResponseEntity<?> updatePassword(@RequestBody MemberSaveRequestDto requestDto){
        try {
            memberService.updatePassword(requestDto.getEmail(), requestDto.getPassword());
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user){
        try {
            MemberLoginResponseDto member = memberService.login(user.get("email"), user.get("password"));
            return new ResponseEntity<MemberLoginResponseDto>(member, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<String>("CHECK EMAIL OR PASSWORD", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 토큰 재발급
    @PostMapping(value = "/api/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestHeader(value="X-AUTH-TOKEN") String token,
            @RequestHeader(value="REFRESH-TOKEN") String refreshToken ) {

        try {
            return new ResponseEntity<MemberLoginResponseDto>(memberService.refreshToken(token, refreshToken), HttpStatus.OK);
        } catch (AccessDeniedException e){
            e.printStackTrace();
            return new ResponseEntity<String>("CHECK TOKEN", HttpStatus.UNAUTHORIZED);
        } catch (IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("RE LOGIN", HttpStatus.PAYMENT_REQUIRED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/api")
    public ResponseEntity<?> practice() {
        return new ResponseEntity<String>("GOOD", HttpStatus.OK);
    }
}
