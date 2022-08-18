package com.sos.owo.controller;


import com.sos.owo.domain.MD5Generator;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.ProfileImg;
import com.sos.owo.dto.*;
import com.sos.owo.service.EmailTokenService;
import com.sos.owo.service.MemberService;
import com.sos.owo.service.ProfileImgService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
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

import javax.imageio.ImageIO;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
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

    private final HttpSession httpSession;

    @Value("${app.fileupload.uploadDir}")
    private String uploadFolder;

    @Value("${app.fileupload.uploadPath}")
    private String uploadPath;

    @ApiOperation(value = "회원가입",notes = "email과 password를 받아서 회원가입을 한다.")
    @PostMapping("/api/auth/join")
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

    @ApiOperation(value = "경쟁모드 최고기록 조회",notes = "memberId를 받아서 해당 사용자의 종목별 최고기록을 조회한다.")
    @ApiImplicitParam(name= "memberId", value="사용자 id", dataType = "int", paramType = "path")
    @GetMapping("/api/user/compete/{memberId}")
    public ResponseEntity<?> findBestScore(@PathVariable("memberId") int memberId, Model model){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
//        try {
            List<Integer> bestScore = memberService.findBestScore(memberId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("경쟁모드 최고기록 조회 성공");
            message.setData(bestScore);
            //return new ResponseEntity<List<Integer>>( memberService.findBestScore(memberId), HttpStatus.OK);
            return new ResponseEntity<>( message, httpHeaders, HttpStatus.OK);
//        } catch (IllegalStateException e){
//            e.printStackTrace();
//            message.setStatus(StatusEnum.BAD_REQUEST);
//            message.setMessage("잘못된 요청");
//            return new ResponseEntity<>(message,httpHeaders,HttpStatus.BAD_REQUEST);
//        } catch (Exception e){
//            e.printStackTrace();
//            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
//            message.setMessage("내부 서버 에러");
//            return new ResponseEntity<>(message,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
//        }

    }

    @ApiOperation(value = "경쟁모드 최고기록 저장",notes = "memberId를 받아서 해당 사용자의 종목별 최고기록을 갱신한다.")
    @PostMapping("/api/user/compete")
    public ResponseEntity<?> saveBestScore(@RequestBody ScoreSaveDto scoreSaveDto){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
//        try {
            message.setStatus(StatusEnum.OK);
            memberService.saveBestScore(scoreSaveDto.getMemberId(), scoreSaveDto.getScore1(), scoreSaveDto.getScore2(), scoreSaveDto.getScore3());
            message.setMessage("경쟁모드 최고기록 저장 성공");
            return new ResponseEntity<>( message, httpHeaders, HttpStatus.OK);
//        } catch (IllegalStateException e){
//            e.printStackTrace();
//            message.setStatus(StatusEnum.BAD_REQUEST);
//            message.setMessage("잘못된 요청");
//            return new ResponseEntity<>(message,httpHeaders,HttpStatus.BAD_REQUEST);
//        } catch (Exception e){
//            e.printStackTrace();
//            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
//            message.setMessage("내부 서버 에러");
//            return new ResponseEntity<>(message,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
//        }

    }

    @ApiOperation(value = "경쟁모드에서 얻은 경험치 저장",notes = "memberId와 경쟁모드 경험치를 받아서 누적시킨다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "point",value = "경쟁모드 경험치",dataType = "int",paramType = "path"),
            @ApiImplicitParam(name = "memberId",value = "사용자 id",dataType = "int",paramType = "path")
    })
    @PutMapping("/api/user/point/{point}/{memberId}")
    public ResponseEntity<?> savePoint(@PathVariable("point") int point, @PathVariable("memberId") int memberId){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
//        try {
//            System.out.println(point);
            memberService.savePoint(point, memberId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("경쟁모드 경험치 저장성공");
            return new ResponseEntity<>( message, httpHeaders, HttpStatus.OK);
//        } catch (IllegalStateException e){
//            e.printStackTrace();
//            message.setStatus(StatusEnum.BAD_REQUEST);
//            message.setMessage("잘못된 요청");
//            return new ResponseEntity<>(message,httpHeaders,HttpStatus.BAD_REQUEST);
//        } catch (Exception e){
//            e.printStackTrace();
//            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
//            message.setMessage("내부 서버 에러");
//            return new ResponseEntity<>(message,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @ApiOperation(value = "자유/영상모드 경험치 저장",notes = "memberId와 자유/영상모드 경험치를 받아서 누적시킨다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exp",value = "자유/영상모드 경험치",dataType = "int",paramType = "path"),
            @ApiImplicitParam(name = "memberId",value = "사용자 id",dataType = "int",paramType = "path")
    })
    @PutMapping("/api/user/exp/{exp}/{memberId}")
    public ResponseEntity<?> saveExp(@PathVariable("exp") int exp, @PathVariable("memberId") int memberId){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
//        try {
            memberService.saveExp(exp, memberId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("자유/영상모드 경험치 저장성공");
            return new ResponseEntity<>( message, httpHeaders, HttpStatus.OK);
//        } catch (IllegalStateException e){
//            e.printStackTrace();
//            message.setStatus(StatusEnum.BAD_REQUEST);
//            message.setMessage("잘못된 요청");
//            return new ResponseEntity<>(message,httpHeaders,HttpStatus.BAD_REQUEST);
//        } catch (Exception e){
//            e.printStackTrace();
//            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
//            message.setMessage("내부 서버 에러");
//            return new ResponseEntity<>(message,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @ApiOperation(value = "비밀번호 찾기 요청",notes = "찾고자 하는 email로 비밀번호 찾기를 요청한다.(이메일 인증)")
    @ApiImplicitParam(name = "email",value = "사용자 email",dataType = "String")
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

    @ApiOperation(value = "비밀번호 변경 요청",notes = "이메일 인증 후 email과 변경하고자 하는 password로 비밀번호 변경 요청을 한다.")
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

    @ApiOperation(value = "로그인 요청",notes = "email과 password로 로그인을 요청한다.")
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody MemberRequestLoginDto memberRequestLoginDto){
        System.out.println(memberRequestLoginDto.getEmail());
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            if(!memberService.checkEnable(memberRequestLoginDto.getEmail())){
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("회원가입 이메일 인증이 필요합니다.");
                return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
            }
            MemberLoginResponseDto member = memberService.login(memberRequestLoginDto.getEmail(), memberRequestLoginDto.getPassword());
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

    @ApiOperation(value = "회원정보를 얻어온다.",notes = "memberId로 해당하는 회원 정보를 얻어온다.")
    @GetMapping("/api/user/userInfo/{memberId}")
    public ResponseEntity<?> memberGet(@PathVariable("memberId") int memberId){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            MemberLoginResponseDto member = memberService.memberGet(memberId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("회원정보 불러오기 성공");
            message.setData(member);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("회원 정보가 없습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 토큰 재발급
    @ApiOperation(value = "access token 재발급 요청",notes = "refresh 토큰으로 access 토큰을 재발급 신청한다.")
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
    @ApiOperation(value = "로그아웃을 요청한다.",notes = "refresh 토큰으로 로그아웃을 요청한다.")
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

    @ApiOperation(value = "사용자 정보 수정 요청",notes = "사용자 정보 수정을 요청한다.")
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


    @ApiOperation(value = "사용자 프로필 사진 수정 요청" ,notes = "사용자의 프로필 사진을 수정 요청한다.")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "file",value = "사용자 이미지 파일"),
                    @ApiImplicitParam(name = "memberId",value = "사용자 memberId"),
            })
    @PostMapping("/api/user/img/{memberId}")
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

    @ApiOperation(value = "사용자 프로필 사진파일 요청" ,notes = "사용자의 프로필 사진파일을 요청한다.")
    @ApiImplicitParam(name = "memberId",value = "사용자 memberId",dataType = "int",paramType = "path")
    @GetMapping("/api/user/{memberId}")
    public ResponseEntity<?> getProfileImg(@PathVariable("memberId") int memberId) throws IOException {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ProfileImg profileImg = profileImgService.getImg(memberId);
        FileDto result = new FileDto(profileImg.getId(),profileImg.getFileOriName(),profileImg.getFileName(),profileImg.getFileUrl());

        message.setStatus(StatusEnum.OK);
        message.setMessage("사용자의 프로필 사진 조회 성공");
        message.setData(result);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 슬로건 수정 요청" ,notes = "사용자의 슬로건의 수정을 요청한다.")
    @PutMapping("/api/user/slogan")
    public ResponseEntity<?> updateSlogan(@RequestBody MemberSloganDto memberSloganDto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        memberService.updateMemberSlogan(memberSloganDto);
        message.setStatus(StatusEnum.OK);
        message.setMessage("사용자 슬로건 수정 성공");
        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }

    @ApiOperation(value = "사용자 슬로건 정보 요청" ,notes = "사용자의 슬로건의 정보를 요청한다.")
    @ApiImplicitParam(name = "memberId",value = "사용자 memberId",dataType = "int",paramType = "path")
    @GetMapping("/api/user/slogan/{memberId}")
    public ResponseEntity<?> getMemberSlogan(@PathVariable("memberId") int memberId){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        try {
        MemberSloganDto memberSloganDto = memberService.getMemberSlogan(memberId);
        message.setStatus(StatusEnum.OK);
        message.setMessage("슬로건 불러오기 성공");
        message.setData(memberSloganDto);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }

    @ApiOperation(value = "사용자 포인트 상위 퍼센티지 요청" ,notes = "사용자 포인트 상위 퍼센티지 정보를 요청한다.")
    @ApiImplicitParam(name = "memberId",value = "사용자 memberId",dataType = "int",paramType = "path")
    @GetMapping("/api/user/point/percentage/{memberId}")
    public ResponseEntity<?> getPercentage(@PathVariable int memberId) throws Exception {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        double percentage = memberService.getPointPercentage(memberId);
        message.setStatus(StatusEnum.OK);
        message.setMessage("포인트 퍼센테지 불러오기 성공");
        message.setData(percentage);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }

    @ApiOperation(value = "사용자 포인트 요청" ,notes = "사용자 포인트 정보를 요청한다.")
    @ApiImplicitParam(name = "memberId",value = "사용자 memberId",dataType = "int",paramType = "path")
    @GetMapping("/api/user/point/{memberId}")
    public ResponseEntity<?> getPoint(@PathVariable int memberId){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int point = memberService.getMemberPoint(memberId);
        message.setStatus(StatusEnum.OK);
        message.setMessage("포인트 불러오기 성공");
        message.setData(point);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }

    @ApiOperation(value = "사용자 신체정보를 요청한다." ,notes = "사용자 신체정보(BMI/기초대사량/하루권장칼로리)에 대한 정보를 요청한다.")
    @ApiImplicitParam(name = "memberId",value = "사용자 memberId",dataType = "int",paramType = "path")
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

    @ApiOperation(value = "소셜로그인 - 멤버정보 요청",notes = "발급받은 accessToken으로 멤버정보를 요청한다.")
    @GetMapping("/api/social")
    public ResponseEntity<?> getMember(
            @RequestHeader(value="X-AUTH-TOKEN") String token) throws Exception {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("access token으로 정보 불러오기 성공");
        message.setData(memberService.getMember(token));
        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }




}
