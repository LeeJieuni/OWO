package com.sos.owo.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MemberSloganDto {
    @ApiModelProperty(example="1")
    @ApiParam(value = "사용자 memberId", type = "int")
    int id;
    @ApiModelProperty(example="주에 4번이상 운동한다.")
    @ApiParam(value = "사용자 slogan", type = "String")
    String slogan;
}
