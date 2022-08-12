package com.sos.owo.dto;

import com.sos.owo.domain.Exercise;
import com.sos.owo.domain.Mode;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class MeetingListRoomResponse {
    int roomId;
    int memberId;
    int manger_point;
    int person;
    boolean secret;
    String password;
    Mode mode;
    String roomName;
    Exercise type;
    String link;

    @Builder
    public MeetingListRoomResponse(int roomId, int memberId, int manger_point, int person, boolean secret, String password, Mode mode, String roomName, Exercise type, String link) {
        this.roomId = roomId;
        this.memberId = memberId;
        this.manger_point = manger_point;
        this.person = person;
        this.secret = secret;
        this.password = password;
        this.mode = mode;
        this.roomName = roomName;
        this.type = type;
        this.link = link;
    }

}
