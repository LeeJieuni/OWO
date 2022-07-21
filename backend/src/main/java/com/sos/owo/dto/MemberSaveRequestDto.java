package com.sos.owo.dto;

import com.sos.owo.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String email;
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
