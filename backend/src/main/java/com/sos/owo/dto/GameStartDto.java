package com.sos.owo.dto;

import com.sos.owo.domain.MeetingRoom;
import com.sos.owo.domain.RoomStatus;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;

import java.time.LocalDateTime;

public class GameStartDto {
    //노필요
    @ApiModelProperty(example = "START")
    @ApiParam(value = "미팅방 상태",type = "RoomStatus")
    private RoomStatus status;

    @ApiModelProperty(example = "")
    @ApiParam(value = "시작시간",type = "LocalDateTime")
    private LocalDateTime startDate;

    public GameStartDto(RoomStatus status, LocalDateTime startDate) {
        this.status = status;
        this.startDate = startDate;
    }

    public MeetingRoom toEntity(){
        MeetingRoom room = new MeetingRoom();
        room.setStatus(status);
        room.setStartDate(startDate);
        return room;
    }
}
