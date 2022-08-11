package com.sos.owo.dto;

import com.sos.owo.domain.Record;
import com.sos.owo.domain.Tag;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
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

    @ApiModelProperty(example="오늘의 운동 완료")
    @ApiParam(value = "기록에 남길 메모",type = "String")
    private String recordMemo;

    @DateTimeFormat(pattern = "yyyyMMdd")
    @ApiModelProperty(example="20220805")
    @ApiParam(value = "기록하는 날짜(연월일 8자)",type = "LocalDate")
    private LocalDate recordDatetime;

    @ApiModelProperty(example="false")
    @ApiParam(value = "기록의 공개여부",type = "boolean")
    private boolean secret;

    @ApiParam(value = "기록에 대한 태그들 리스트",type = "List<String>")
    private List<String> tagList;

    @ApiParam(value = "이미지 파일 명",type = "String")
    private String fileOriName;

    @ApiParam(value = "이미지 파일의 url",type = "String")
    private String fileUrl;

    @ApiParam(value = "운동한 시간(단위 : 분)",type = "int")
    private int recordTime;

    @Builder
    public RecordDto(String recordMemo, LocalDate recordDatetime, boolean secret, List<String> tagList, String fileOriName, String fileUrl,int recordTime) {
        this.recordMemo = recordMemo;
        this.recordDatetime = recordDatetime;
        this.secret = secret;
        this.tagList = tagList;
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
        this.recordTime=recordTime;
    }

    

    public Record toEntity()
    {
        return Record.builder().recordMemo(recordMemo).recordDatetime(recordDatetime).recordSecret(secret).recordTime(recordTime).build();
    }

}
