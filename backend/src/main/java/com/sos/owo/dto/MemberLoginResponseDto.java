package com.sos.owo.dto;

import com.sos.owo.domain.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter
@AllArgsConstructor
@Builder
public class MemberLoginResponseDto {
    @ApiModelProperty(example="hithere1012@naver.com")
    String email;
    @ApiModelProperty(example="발급받은 access token")
    String accessToken;
    @ApiModelProperty(example="발급받은 refresh token")
    String refreshToken;
    @ApiModelProperty(example="1")
    int id;
    @ApiModelProperty(example="유저 닉네임")
    String nick;
    @ApiModelProperty(example="FEMALE")
    Gender gender;
    @ApiModelProperty(example="20")
    int age = 0;
    @ApiModelProperty(example="170")
    double height = 0;
    @ApiModelProperty(example="50")
    double weight = 0;
    @ApiModelProperty(example="1")
    int activityNum = 0;
    @ApiModelProperty(example="5")
    int activityHour = 0;
    @ApiModelProperty(example="2")
    int activityLevel = 0;
}
