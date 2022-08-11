package com.sos.owo.dto;

import com.sos.owo.domain.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String pw;

    public SessionUser(Member member){
        this.name = member.getNick();
        this.email = member.getEmail();
        this.pw = member.getPw();
    }
}
