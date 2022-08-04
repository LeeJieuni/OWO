package com.sos.owo.dto;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Member;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalResponseDto {
    private int goalId;
    private String exercise;
    private int memberId;
    private int hour;


}
