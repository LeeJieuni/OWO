package com.sos.owo.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RecordPlaceDto {
    @DateTimeFormat(pattern = "yyyyMMdd")
    @ApiModelProperty(example="20220805")
    @ApiParam(value = "운동한 날짜", type = "LocalDate")
    private LocalDate datetime;

    @ApiModelProperty(example="1")
    @ApiParam(value = "그 판의 등수",type = "boolean")
    private int place;

    @ApiModelProperty(example="120")
    @ApiParam(value = "그 날의 운동 총 시간(단위: 분)",type = "int")
    private int dayTimeSum;


}
