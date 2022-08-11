package com.sos.owo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailToken {

    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L; // 이메일 토큰 만료 시간

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name ="uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String id;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    private boolean expired;

    @Column(name = "member_id")
    private int memberId;

    // 이메일 인증 토큰 생성
    public static EmailToken createEmailToekn(int memberId){
        EmailToken emailToken = new EmailToken();
        // 토큰은 5분 후 만료
        emailToken.expirationDate = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE);
        emailToken.expired = false;
        emailToken.memberId = memberId;

        return emailToken;
    }

    // 토큰 만료
    public void setTokenToUsed(){
        this.expired = true;
    }

}
