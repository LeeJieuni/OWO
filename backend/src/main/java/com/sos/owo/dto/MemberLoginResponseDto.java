package com.sos.owo.dto;

import com.sos.owo.domain.Gender;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter
@AllArgsConstructor
@Builder
public class MemberLoginResponseDto {
    String email;
    String accessToken;
    String refreshToken;
    int id;
    String nick;
    Gender gender;
    int age = 0;
    double height = 0;
    double weight = 0;
    int activityNum = 0;
    int activityHour = 0;
    int activityLevel = 0;
}
