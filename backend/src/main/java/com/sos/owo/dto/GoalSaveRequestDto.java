package com.sos.owo.dto;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Goal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoalSaveRequestDto {
    private Exercise exercise;
    private int hour;


    @Builder
    public GoalSaveRequestDto(Exercise exercise, int hour) {
        this.exercise = exercise;
        this.hour = hour;

    }

    public Goal toEntity(){
        return Goal.builder().exercise(exercise).hour(hour).build();
    }
}
