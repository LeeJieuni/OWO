package com.sos.owo.dto;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Goal;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoalSaveRequestDto {

    @ApiModelProperty(example = "YOGA")
    @ApiParam(value = "운동 종목 이름",type = "EXERCISE")
    private Exercise exercise;
    @ApiModelProperty(example = "2")
    @ApiParam(value = "운동 종목에 대한 목표 시간(시 단위)",type = "int")
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
