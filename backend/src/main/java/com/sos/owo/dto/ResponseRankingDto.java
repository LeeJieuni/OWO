package com.sos.owo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class ResponseRankingDto {
    private String name;
    private int member_id;
    private int score;
    public static ResponseRankingDto convertToResponseRankingDto(ZSetOperations.TypedTuple typedTuple){
        ResponseRankingDto responseRankingDto=new ResponseRankingDto();
        responseRankingDto.member_id=Integer.parseInt(typedTuple.getValue().toString());
        responseRankingDto.score=typedTuple.getScore().intValue();
        return responseRankingDto;
    }
}
