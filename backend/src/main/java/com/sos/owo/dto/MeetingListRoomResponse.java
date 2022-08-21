package com.sos.owo.dto;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Mode;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class MeetingListRoomResponse {
    @ApiParam(value = "미팅방 번호",type = "int")
    int roomId;
    @ApiParam(value = "멤버 회원 memberId",type = "int")
    int memberId;
    @ApiParam(value = "방장 상위 퍼센테이지",type = "double")
    double manger_percentage;
    @ApiParam(value = "방 인원 수",type = "int")
    int person;
    @ApiParam(value = "비밀방 여부",type = "boolean")
    boolean secret;
    @ApiParam(value = "비밀방일 때 비밀번호",type = "String")
    String password;
    @ApiParam(value = "방 모드(FREE, STREAMING, GAME)",type = "Mode")
    Mode mode;
    @ApiParam(value = "방 이름",type = "String")
    String roomName;
    @ApiParam(value = "방의 운동 타입(AEROBIC, HEALTH, STRETCHING, HOME, BODYWEIGHT, YOGA, PILATES, GAME, ETC)",type = "RoomStatus")
    Exercise type;
    @ApiParam(value = "방 모드가 STREAMING일 때 URL",type = "String")
    String link;

    @Builder
    public MeetingListRoomResponse(int roomId, int memberId, double manger_percentage, int person, boolean secret, String password, Mode mode, String roomName, Exercise type, String link) {
        this.roomId = roomId;
        this.memberId = memberId;
        this.manger_percentage = manger_percentage;
        this.person = person;
        this.secret = secret;
        this.password = password;
        this.mode = mode;
        this.roomName = roomName;
        this.type = type;
        this.link = link;
    }

}
