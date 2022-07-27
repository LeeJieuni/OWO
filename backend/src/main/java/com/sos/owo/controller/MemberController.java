package com.sos.owo.controller;


import com.sos.owo.domain.MD5Generator;
import com.sos.owo.domain.Member;
import com.sos.owo.dto.*;
import com.sos.owo.service.EmailTokenService;
import com.sos.owo.service.MemberService;
import com.sos.owo.service.ProfileImgService;
import io.swagger.annotations.ApiOperation;
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

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
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

    private final HttpSession httpSession;

    @Value("${app.fileupload.uploadDir}")
    private String uploadFolder;

    @Value("${app.fileupload.uploadPath}")
    private String uploadPath;


    @PostMapping("/auth/join")
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

    @ApiOperation(value = "경쟁모드 최고기록 조회",notes = "memberId를 받아서 해당 사용자의 종목별 최고기록을 조회한다.")
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

    @ApiOperation(value = "경쟁모드에서 얻은 경험치 저장",notes = "memberId와 경쟁모드 경험치를 받아서 누적시킨다.")
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

    @ApiOperation(value = "자유/영상모드 경험치 저장",notes = "memberId와 자유/영상모드 경험치를 받아서 누적시킨다.")
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

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user){
        try {
            if(!memberService.checkEnable(user.get("email"))){
                return new ResponseEntity<String>("DISABLED", HttpStatus.BAD_REQUEST);
            }
            MemberLoginResponseDto member = memberService.login(user.get("email"), user.get("password"));
            return new ResponseEntity<MemberLoginResponseDto>(member, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e){
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

    @GetMapping("/api/logout")
    public ResponseEntity<?> logout(@RequestHeader(value="REFRESH-TOKEN") String refreshToken) {
        try {
            memberService.logoutMember(refreshToken);
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>("CHECK TOKEN", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/user")
    public ResponseEntity<?> updateMember(@RequestBody MemberUpdateDto memberUpdateDto, HttpServletRequest request){

        try {
            memberService.updateMember(memberUpdateDto);
            return new ResponseEntity<MemberUpdateDto>(memberUpdateDto, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("CHECK EMAIL OR PASSWORD", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/user/{memberId}")
    public ResponseEntity<?> updateProfileImg(@RequestParam("file") MultipartFile file, @PathVariable("memberId") int memberId) {

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
                return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("CHECK FILE", HttpStatus.BAD_REQUEST);
            }

        } catch (IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("CHECK EMAIL", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
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
        try {
            memberService.updateMemberSlogan(memberSloganDto);
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/user/slogan/{memberId}")
    public ResponseEntity<?> getMemberSlogan(@PathVariable("memberId") int memberId){
        try {
            MemberSloganDto memberSloganDto = memberService.getMemberSlogan(memberId);
            return new ResponseEntity<MemberSloganDto>(memberSloganDto, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/user/point/percentage/{memberId}")
    public ResponseEntity<?> getPercentage(@PathVariable int memberId){
        try {
            double percentage = memberService.getPointPercentage(memberId);
            return new ResponseEntity<Double>(percentage, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/user/point/{memberId}")
    public ResponseEntity<?> getPoint(@PathVariable int memberId){
        try {
            int point = memberService.getMemberPoint(memberId);
            return new ResponseEntity<Integer>(point, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/user/bmi/{memberId}")
    public ResponseEntity<?> getBMI(@PathVariable int memberId){
        try {
            MemberBodyDto memberBodyDto = memberService.getMemberBodyInformation(memberId);
            return new ResponseEntity<MemberBodyDto>(memberBodyDto, HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<String>("UPDATE INFORMATION", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/oauth/google")
    public ResponseEntity<?> GoogleLogin() throws IOException {
        try {
            SessionUser user = (SessionUser)httpSession.getAttribute("user");
            System.out.println(user);
            return new ResponseEntity<SessionUser>(user, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<String>("구글 로그인 오류", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/oauth/naver")
//    public ResponseEntity<?> NaverLogin(Model model) throws IOException {
//        try {
//            SessionUser user = (SessionUser)httpSession.getAttribute("user");
//            return new ResponseEntity<SessionUser>(user, HttpStatus.OK);
//        } catch (IllegalArgumentException | IllegalStateException e){
//            e.printStackTrace();
//            return new ResponseEntity<String>("네이버 로그인 오류", HttpStatus.BAD_REQUEST);
//        } catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<String>("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }






}
