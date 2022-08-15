package com.sos.owo.dto;

import com.sos.owo.domain.Exercise;
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
@AllArgsConstructor
@ToString
public class RecordResponseDto {
    @ApiModelProperty(example="1")
    @ApiParam(value = "기록 id", type = "int")
    private int recordId;

    @ApiModelProperty(example="1")
    @ApiParam(value = "사용자 id", type = "int")
    private int memberId;

    @ApiModelProperty(example="1")
    @ApiParam(value = "미팅룸 id", type = "int")
    private int meetingRoomId;

    @ApiParam(value = "기록에 대한 태그들 리스트",type = "List<TagResponseDto>")
    private List<TagResponseDto> tags;

    @ApiParam(value = "운동한 시간(분 단위)", type = "int")
    private int recordHour; // 운동 시간(분단위)

    private int imgId;

    @ApiModelProperty(example="오늘의 운동 완료")
    @ApiParam(value = "기록에 남길 메모",type = "String")
    private String recordMemo;

    @DateTimeFormat(pattern = "yyyyMMdd")
    @ApiModelProperty(example="20220805")
    @ApiParam(value = "운동한 날짜", type = "LocalDate")
    private LocalDate datetime;

    @ApiModelProperty(example="false")
    @ApiParam(value = "기록의 공개여부",type = "boolean")
    private boolean recordSecret;

    @ApiModelProperty(example="YOGA")
    @ApiParam(value = "운동 종류",type = "String")
    private String exercise;

    @ApiModelProperty(example="1")
    @ApiParam(value = "그 판의 등수",type = "int")
    private int place;

}
