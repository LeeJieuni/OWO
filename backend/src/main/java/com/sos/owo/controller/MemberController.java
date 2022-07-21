package com.sos.owo.controller;

import com.sos.owo.dto.MemberSaveRequestDto;
import com.sos.owo.dto.MemberResponseDto;
import com.sos.owo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/auth/join")
    public ResponseEntity<?> signUp(@RequestBody MemberSaveRequestDto requestDto){
        try {
            int id = memberService.join(requestDto.toEntity());
            return new ResponseEntity<MemberResponseDto>(memberService.findOne(id), HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<String>("OVERLAP", HttpStatus.CONFLICT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
