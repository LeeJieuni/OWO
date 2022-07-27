package com.sos.owo.controller;


import com.sos.owo.domain.MD5Generator;
import com.sos.owo.domain.Member;
import com.sos.owo.dto.*;
import com.sos.owo.service.EmailTokenService;
import com.sos.owo.service.MemberService;
import com.sos.owo.service.ProfileImgService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailTokenService emailTokenService;
    private final ProfileImgService profileImgService;



    @PostMapping("/auth/join")
    public ResponseEntity<?> signUp(@RequestBody MemberSaveRequestDto requestDto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            int id = memberService.join(requestDto.toEntity());
            emailTokenService.createEmailToken(id, requestDto.getEmail());
            message.setStatus(StatusEnum.OK);
            message.setMessage("회원가입 성공");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("가입 이메일이 중복됩니다.");
            return new ResponseEntity<String>("OVERLAP", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
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
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            Member member = memberService.checkEmail(email);
            String emailTokenId= emailTokenService.createPasswordEmailToken(member.getId(), email);
            message.setStatus(StatusEnum.OK);
            message.setMessage("비밀번호 찾기 이메일 인증 발송");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("해당 이메일이 존재하지 않습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/api/auth/password")
    public ResponseEntity<?> updatePassword(@RequestBody MemberSaveRequestDto requestDto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            memberService.updatePassword(requestDto.getEmail(), requestDto.getPassword());
            message.setStatus(StatusEnum.OK);
            message.setMessage("비밀번호 변경 성공");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            if(!memberService.checkEnable(user.get("email"))){
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("회원가입 이메일 인증이 필요합니다.");
                return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
            }
            MemberLoginResponseDto member = memberService.login(user.get("email"), user.get("password"));
            message.setStatus(StatusEnum.OK);
            message.setMessage("로그인 성공");
            message.setData(member);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("이메일 혹은 비밀번호가 맞지 않습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 토큰 재발급
    @PostMapping(value = "/api/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestHeader(value="X-AUTH-TOKEN") String token,
            @RequestHeader(value="REFRESH-TOKEN") String refreshToken ) {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try {
            message.setStatus(StatusEnum.OK);
            message.setMessage("ACCESS TOKEN 재발급 성공");
            message.setData(memberService.refreshToken(token, refreshToken));
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (AccessDeniedException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage("REFRESH TOKEN이 일치하지 않습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("RE LOGIN", HttpStatus.PAYMENT_REQUIRED);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/api/logout")
    public ResponseEntity<?> logout(@RequestHeader(value="REFRESH-TOKEN") String refreshToken) {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            memberService.logoutMember(refreshToken);
            message.setStatus(StatusEnum.OK);
            message.setMessage("로그아웃 성공");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("ACCESS TOKEN이 일치하지 않습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/user")
    public ResponseEntity<?> updateMember(@RequestBody MemberUpdateDto memberUpdateDto, HttpServletRequest request){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            memberService.updateMember(memberUpdateDto);
            message.setStatus(StatusEnum.OK);
            message.setMessage("사용자 정보 수정 완료");
            message.setData(memberUpdateDto);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e){
            e.printStackTrace();
            memberService.updateMember(memberUpdateDto);
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("사용자 이메일 혹은 비밀번호가 맞지 않습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/user/{memberId}")
    public ResponseEntity<?> updateProfileImg(@RequestParam("file") MultipartFile file, @PathVariable("memberId") int memberId) {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            if (file != null) {
                String fileOriName = file.getOriginalFilename();
                String fileName = new MD5Generator(fileOriName).toString();
                String savePath = System.getProperty("user.dir") +"\\src\\main\\resources\\static\\img\\profile";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String fileUrl = savePath + "\\" + fileName;
                file.transferTo(new File(fileUrl));
                FileDto fileDto = profileImgService.saveFile(memberId, fileOriName, fileName, fileUrl);
                message.setStatus(StatusEnum.OK);
                message.setMessage("프로필 사진 수정 성공");
                return new ResponseEntity<>(message, headers, HttpStatus.OK);
            } else {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("이미지 파일 오류 발생");
                return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
            }

        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("해당 이메일이 존재하지 않습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/user/{memberId}")
    public ResponseEntity<?> getProfileImg(@PathVariable("memberId") int memberId) throws IOException {
        FileDto fileDto = profileImgService.getFile(memberId);
        if(fileDto == null){
            return new ResponseEntity<String>("null", HttpStatus.OK);
        }
        Path path = Paths.get(fileDto.getFileUrl());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDto.getFileOriName() + "\"")
                .body(resource);
    }

    @PutMapping("/api/user/slogan")
    public ResponseEntity<?> updateSlogan(@RequestBody MemberSloganDto memberSloganDto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            memberService.updateMemberSlogan(memberSloganDto);
            message.setStatus(StatusEnum.OK);
            message.setMessage("사용자 슬로건 수정 성공");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/user/slogan/{memberId}")
    public ResponseEntity<?> getMemberSlogan(@PathVariable("memberId") int memberId){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            MemberSloganDto memberSloganDto = memberService.getMemberSlogan(memberId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("프로필 사진 불러오기 성공");
            message.setData(memberSloganDto);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/user/point/percentage/{memberId}")
    public ResponseEntity<?> getPercentage(@PathVariable int memberId){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            double percentage = memberService.getPointPercentage(memberId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("포인트 퍼센테지 불러오기 성공");
            message.setData(percentage);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/user/point/{memberId}")
    public ResponseEntity<?> getPoint(@PathVariable int memberId){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            int point = memberService.getMemberPoint(memberId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("포인트 불러오기 성공");
            message.setData(point);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e){
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/user/bmi/{memberId}")
    public ResponseEntity<?> getBMI(@PathVariable int memberId){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            MemberBodyDto memberBodyDto = memberService.getMemberBodyInformation(memberId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("신체정보 불러오기 성공");
            message.setData(memberBodyDto);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("신체정보 입력이 필요합니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
