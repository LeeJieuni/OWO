package com.sos.owo.service;

import com.sos.owo.domain.repository.MeetingRoomRepository;
import com.sos.owo.dto.MeetingRoomMakeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingRoomService {
    private MeetingRoomRepository meetingRoomRepository;

    public void int createRoom(MeetingRoomMakeRequestDto requestDto) {

    }
}
