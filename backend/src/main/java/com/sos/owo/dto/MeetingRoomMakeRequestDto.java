package com.sos.owo.dto;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Mode;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter
@NoArgsConstructor
public class MeetingRoomMakeRequestDto {
    @ApiParam(value = "사용자 memberId", required = true)
    int memberId;
    @ApiParam(value = "비밀방 여부", required = true)
    boolean secret;
    @ApiParam(value = "비밀방 - 비밀번호")
    String password;
    @ApiParam(value = "방 모드(FREE, STREAMING, GAME)", required = true)
    Mode mode;
    @ApiParam(value = "방 이름", required = true)
    String roomName;
    @ApiParam(value = "운동 타입(AEROBIC, HEALTH, STRETCHING, HOME, BODYWEIGHT, YOGA, PILATES, GAME, ETC)", required = true)
    Exercise type;
    @ApiParam(value = "STRETCHING 시에 유튜브 url")
    String link;

    @Builder
    public MeetingRoomMakeRequestDto(int memberId, boolean secret, String password, Mode mode, String roomName, Exercise type, String link) {
        this.memberId = memberId;
        this.secret = secret;
        this.password = password;
        this.mode = mode;
        this.roomName = roomName;
        this.type = type;
        this.link = link;
    }

}
