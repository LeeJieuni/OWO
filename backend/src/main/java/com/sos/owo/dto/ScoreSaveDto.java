package com.sos.owo.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class ScoreSaveDto {
    @ApiModelProperty(example="2")
    @ApiParam(value = "memberId",type = "int")
    public int memberId;
    @ApiModelProperty(example="10")
    @ApiParam(value = "score1",type = "int")
    public int score1;

    @ApiModelProperty(example="10")
    @ApiParam(value = "score2",type = "int")
    public int score2;

    @ApiModelProperty(example="10")
    @ApiParam(value = "score3",type = "int")
    public int score3;


}
