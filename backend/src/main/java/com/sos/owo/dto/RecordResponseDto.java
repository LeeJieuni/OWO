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
    @ApiParam(value = "기록 id", type = "int")
    private int recordId;
    @ApiParam(value = "사용자 id", type = "int")
    private int memberId;
    @ApiParam(value = "미팅룸 id", type = "int")
    private int meetingRoomId;
    private List<TagResponseDto> tags;
    @ApiParam(value = "운동한 시간(분 단위)", type = "int")
    private int recordHour; // 운동 시간(분단위)
//    private int imgId;
    private String recordMemo;
    @DateTimeFormat(pattern = "yyyyMMdd")
    @ApiModelProperty(example="20220805")
    @ApiParam(value = "운동한 날짜", type = "LocalDate")
    private LocalDate datetime;
//    private LocalDateTime datetime;
    private boolean recordSecret;
    private String exercise;


}
