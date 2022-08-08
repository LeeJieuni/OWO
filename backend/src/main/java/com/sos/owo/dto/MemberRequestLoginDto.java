package com.sos.owo.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberRequestLoginDto {
    @ApiParam(value = "사용자 email", required = true)
    @ApiModelProperty(example="hithere1012@naver.com")
    private String email;
    @ApiParam(value = "사용자 password", required = true)
    @ApiModelProperty(example="1234")
    private String password;
}
