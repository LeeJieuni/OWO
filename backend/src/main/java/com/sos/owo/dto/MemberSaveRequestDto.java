package com.sos.owo.dto;

import com.sos.owo.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {
    @ApiModelProperty(example="hithere1012@naver.com")
    @ApiParam(value = "사용자 email", type = "String")
    private String email;
    @ApiModelProperty(example="1234")
    @ApiParam(value = "사용자 password", type = "String")
    private String password;

    @Builder
    public MemberSaveRequestDto(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Member toEntity(){
        Member member = new Member();
        member.setEmail(email);
        member.setPw(password);
        return member;
    }

}
