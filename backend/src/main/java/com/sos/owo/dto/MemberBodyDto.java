package com.sos.owo.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberBodyDto {
    @ApiModelProperty(example="20.0")
    @ApiParam(value = "사용자 BMI", type = "double")
    double bmi;
    @ApiModelProperty(example="1300")
    @ApiParam(value = "사용자 기초대사량", type = "double")
    double bmr;
    @ApiModelProperty(example="1600")
    @ApiParam(value = "사용자 하루권장섭취칼로리", type = "double")
    double caloriePerDay;
}
