package com.sos.owo.dto;

import com.sos.owo.domain.Gender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MemberUpdateDto {
    String email;
    int id;
    String nick;
    Gender gender;
    int age;
    double height;
    double weight;
    int activityNum;
    int activityHour;
    int activityLevel;
}
