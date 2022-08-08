package com.sos.owo.dto;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalResponseDto {
    @ApiModelProperty(example = "1")
    @ApiParam(value = "목표 goal id",type = "int")
    private int goalId;

    @ApiModelProperty(example = "YOGA")
    @ApiParam(value = "운동 종목 이름",type = "String")
    private String exercise;

    @ApiModelProperty(example = "10")
    @ApiParam(value = "사용자 id",type = "int")
    private int memberId;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "운동 종목에 대한 목표 시간(시 단위)",type = "int")
    private int hour;


}
