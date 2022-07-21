package com.sos.owo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class MemberResponseDto {
    int id;
    String email;
    String pw;
    String nick;
    String gender;
    int age;
    double height;
    double weight;
    double activityNum;
    double activityHour;

}
