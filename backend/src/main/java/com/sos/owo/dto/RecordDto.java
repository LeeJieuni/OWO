package com.sos.owo.dto;

import com.sos.owo.domain.Record;
import com.sos.owo.domain.Tag;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class RecordDto {
//    @ApiModelProperty(example="2")
//    private int recordHour;
    @ApiModelProperty(example="오늘의 운동 완료")
    private String recordMemo;
//    @ApiModelProperty(example="2022-08-04 03:11:11")
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate recordDatetime;
//    private LocalDateTime recordDatetime;
    @ApiModelProperty(example="false")
    private boolean secret;
//    @ApiModelProperty(example="{'하체'}")
    private List<String> tagList;

    @Builder
    public RecordDto(String recordMemo, LocalDate recordDatetime, boolean secret, List<String> tagList) {
//        this.recordHour = recordHour;
        this.recordMemo = recordMemo;
        this.recordDatetime = recordDatetime;
        this.secret = secret;
        this.tagList = tagList;
    }




    public Record toEntity()
    {
        return Record.builder().recordMemo(recordMemo).recordDatetime(recordDatetime).recordSecret(secret).build();
    }

}
