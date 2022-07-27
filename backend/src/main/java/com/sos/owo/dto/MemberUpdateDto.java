package com.sos.owo.dto;

import com.sos.owo.domain.Gender;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MemberUpdateDto {
    @ApiModelProperty(example="hithere1012@naver.com")
    @ApiParam(value = "사용자 email", type = "String")
    String email;
    @ApiModelProperty(example="1")
    @ApiParam(value = "사용자 memberId", type = "int")
    int id;
    @ApiModelProperty(example="유저 닉네임")
    @ApiParam(value = "사용자 nickname", type = "String")
    String nick;
    @ApiModelProperty(example="MALE")
    @ApiParam(value = "사용자 gender(MALE/FEMALE)", type = "String")
    Gender gender;
    @ApiModelProperty(example="20")
    @ApiParam(value = "사용자 age", type = "int")
    int age;
    @ApiModelProperty(example="170")
    @ApiParam(value = "사용자 height", type = "double")
    double height;
    @ApiModelProperty(example="50")
    @ApiParam(value = "사용자 weight", type = "double")
    double weight;
    @ApiModelProperty(example="1")
    @ApiParam(value = "사용자 주 활동 횟수", type = "int")
    int activityNum;
    @ApiModelProperty(example="5")
    @ApiParam(value = "사용자 주 활동 시간", type = "int")
    int activityHour;
    @ApiModelProperty(example="2")
    @ApiParam(value = "사용자 활동 레벨(1,2,3,4)", type = "int")
    int activityLevel;
}
