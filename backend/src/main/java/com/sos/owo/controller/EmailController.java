package com.sos.owo.controller;

import com.sos.owo.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class EmailController {
    private final EmailService emailService;


    // 인증 URL이 담긴 이메일을 전송받은 사용자가 URL을 클릭 시 해당 URI로 매핑됨
    @GetMapping("/confirm-email")
    public ResponseEntity<String> viewConfirmEmail(@Valid @RequestParam String token){
        try {
            boolean result = emailService.verifyEmail(token);
            return new ResponseEntity<>("이메일 인증 성공", HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>("이메일 인증 실패", HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/confirm-email-password")
    public ResponseEntity<String> viewConfirmPassword(@Valid @RequestParam String token){
        try {
            emailService.verifyPasswordToken(token);
            return new ResponseEntity<>("이메일 인증 성공", HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>("이메일 인증 실패", HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
