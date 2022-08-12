package com.sos.owo.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class MemberProfileImgDto {
    @ApiModelProperty(example="apple.png")
    @ApiParam(value = "이미지 파일 명",type = "String")
    private String fileOriName;

    @ApiModelProperty(example="base64인코딩된것")
    @ApiParam(value = "이미지 파일의 url",type = "String")
    private String fileUrl;

    public MemberProfileImgDto(String fileOriName, String fileUrl) {
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
    }


}
