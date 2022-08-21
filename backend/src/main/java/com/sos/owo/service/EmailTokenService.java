package com.sos.owo.service;

import com.sos.owo.domain.EmailToken;
import com.sos.owo.domain.repository.EmailTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class EmailTokenService {
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private EmailTokenRepository emailTokenRepository;
    @Autowired
    private JavaMailSender mailSender;




    // 이메일 토큰 생성(회원가입)
    @Async
    public String createEmailToken(int memberId, String receiverEmail)  {
        Assert.notNull(memberId, "memberId는 필수입니다.");
        Assert.hasText(receiverEmail, "receiverEmail은 필수입니다.");

        // 이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToekn(memberId);
        emailTokenRepository.save(emailToken);
        MimeMessage message = mailSender.createMimeMessage();

        StringBuffer emailcontent = new StringBuffer();
        emailcontent.append("<!DOCTYPE html>");
        emailcontent.append("<html>");
        emailcontent.append("<head>");
        emailcontent.append("</head>");
        emailcontent.append("<body>");
        emailcontent.append(
                " <div" 																																																	+
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 500px; height: 600px; border-top: 4px solid #4E8AFF; margin: 100px auto; padding: 30px 0; box-sizing: border-box; color: #000000;\">"		+
//                "<style> img { display: block; margin: 0px auto; } </style>" +
                        "<img src=\"https://github.com/Seunghui98/AlgorithmPython/blob/master/KakaoTalk_20220815_094148325.png?raw=true\" " +
                        "style=\"margin: 0 auto; display: block;\" width=\"350\"/>" +
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400; text-align: center;\">"																															+
//                "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">HOMEDONG</span><br />"																												+
                        "		<span style=\"color: #4E8AFF; text-align: center;\">메일인증</span> 안내입니다."																																				+
                        "	</h1>\n"																																																+
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px; color: #000000;\">"																													+
                        "		#오운완에 가입해 주셔서 진심으로 감사드립니다.<br />"																																						+
                        "		아래 <b style=\"color: #4E8AFF\">'메일 인증'</b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />"																													+
                        "		감사합니다."																																															+
                        "	</p>"																																																	+
                        "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\""																																	+
                        "	href=\"" + "https://i7c202.p.ssafy.io:8282/api/confirm-email?token=" +emailToken.getId()+ "\">"														+
                        "<div style=\"margin: 0 auto;\">" +
                        "		<p"																																																	+
                        "			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 50px; background: #4E8AFF; line-height: 45px; vertical-align: middle; font-size: 16px;\">"							+
                        "			메일 인증</p>" +
                        "</div>"+
                        "	</a>"																																																	+
                        "	<div style=\"border-top: 1px solid #4E8AFF; padding: 5px;\"></div>"																																		+
                        " </div>"
        );
        emailcontent.append("</body>");
        emailcontent.append("</html>");


        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setSubject("[#오운완 - 회원가입 이메일 인증]");
            messageHelper.setTo(receiverEmail);
            messageHelper.setFrom("owo202202202@gmail.com", "#오운완");
            messageHelper.setText(emailcontent.toString(),true);
            mailSender.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }




        // 인증메일 전송 시 토큰 변환
        return emailToken.getId();
    }


    // 이메일 토큰 생성 (회원가입)
    public String createPasswordEmailToken(int memberId, String receiverEmail){
        Assert.notNull(memberId, "memberId는 필수입니다.");
        Assert.hasText(receiverEmail, "receiverEmail은 필수입니다.");

        // 이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToekn(memberId);
        emailTokenRepository.save(emailToken);
        MimeMessage message = mailSender.createMimeMessage();

        StringBuffer emailcontent = new StringBuffer();
        emailcontent.append("<!DOCTYPE html>");
        emailcontent.append("<html>");
        emailcontent.append("<head>");
        emailcontent.append("</head>");
        emailcontent.append("<body>");
        emailcontent.append(
                " <div" 																																																	+
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 500px; height: 600px; border-top: 4px solid #4E8AFF; margin: 100px auto; padding: 30px 0; box-sizing: border-box; color: #000000;\">"		+
//                "<style> img { display: block; margin: 0px auto; } </style>" +
                        "<img src=\"https://github.com/Seunghui98/AlgorithmPython/blob/master/KakaoTalk_20220815_094148325.png?raw=true\" " +
                        "style=\"margin: 0 auto; display: block;\" width=\"350\"/>" +
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400; text-align: center;\">"																															+
//                "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">HOMEDONG</span><br />"																												+
                        "		<span style=\"color: #4E8AFF; text-align: center;\">메일인증</span> 안내입니다."																																				+
                        "	</h1>\n"																																																+
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px; color: #000000;\">"																													+
                        "		#오운완에 가입해 주셔서 진심으로 감사드립니다.<br />"																																						+
                        "		아래 <b style=\"color: #4E8AFF\">'메일 인증'</b> 버튼을 클릭하여 비밀번호 인증을 완료해 주세요.<br />"																													+
                        "		감사합니다."																																															+
                        "	</p>"																																																	+
                        "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\""																																	+
                        "	href=\"" + "https://i7c202.p.ssafy.io:8282/api/confirm-email?token=" +emailToken.getId()+ "\">"														+
                        "<div style=\"margin: 0 auto;\">" +
                        "		<p"																																																	+
                        "			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 50px; background: #4E8AFF; line-height: 45px; vertical-align: middle; font-size: 16px;\">"							+
                        "			메일 인증</p>" +
                        "</div>"+
                        "	</a>"																																																	+
                        "	<div style=\"border-top: 1px solid #4E8AFF; padding: 5px;\"></div>"																																		+
                        " </div>"
        );
        emailcontent.append("</body>");
        emailcontent.append("</html>");


        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setSubject("[#오운완 - 비밀번호 변경 이메일 인증]");
            messageHelper.setTo(receiverEmail);
            messageHelper.setFrom("owo202202202@gmail.com", "#오운완");
            messageHelper.setText(emailcontent.toString(),true);
            mailSender.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }

        // 인증메일 전송 시 토큰 변환
        return emailToken.getId();
    }



    // 유효한 토큰 가져오기
    public EmailToken findByIdAndExpirationDateAfterAndExpired(String emailTokenId)  {
        Optional<EmailToken> emailToken = emailTokenRepository
                .findByIdAndExpirationDateAfterAndExpired(emailTokenId, LocalDateTime.now(), false);

        // 토큰이 없다면 예외 발생
        return emailToken.orElseThrow(() -> new IllegalArgumentException("토큰이 존재하지 않습니다."));
    }

}