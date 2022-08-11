package com.sos.owo.dto;

import com.sos.owo.domain.Record;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagResponseDto {
    @ApiModelProperty(example="1")
    @ApiParam(value = "태그 id",type = "int")
    private int tagId;

    @ApiModelProperty(example="1")
    @ApiParam(value = "기록 id",type = "int")
    private int recordId;

    @ApiModelProperty(example="YOGA")
    @ApiParam(value = "태그 내용",type = "String")
    private String tagContent;
}
