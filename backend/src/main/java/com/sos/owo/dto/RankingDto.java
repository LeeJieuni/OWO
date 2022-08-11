package com.sos.owo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class RankingDto {
    public int rank;
    public int member_id;
    public int time;

    @Builder
    public RankingDto(int rank, int member_id, int time){
        this.rank = rank;
        this.member_id = member_id;
        this.time = time;
    }
}
