package com.sos.owo.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class EnterRoomDto {
    @ApiModelProperty(example="3")
    @ApiParam(value = "미팅방 번호",type = "int")
    public int roomId;
    @ApiModelProperty(example="1234")
    @ApiParam(value = "미팅방 비밀번호(비밀방 아닐 경우 빈 문자열)",type = "String")
    public String password;


}
