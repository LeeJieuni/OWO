package com.sos.owo.service;

import com.sos.owo.domain.MeetingRoom;
import com.sos.owo.domain.Mode;
import com.sos.owo.domain.RoomStatus;
import com.sos.owo.domain.repository.MeetingRoomRepository;
import com.sos.owo.dto.MeetingRoomMakeRequestDto;
import com.sos.owo.error.Exception.custom.SomethingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MeetingRoomService {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Transactional
    public int createRoom(MeetingRoomMakeRequestDto requestDto) {
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setManager(requestDto.getMemberId());
        meetingRoom.setSecret(requestDto.isSecret());
        meetingRoom.setStatus(RoomStatus.WAIT);
        meetingRoom.setPassword(requestDto.getPassword());
        meetingRoom.setMode(requestDto.getMode());
        meetingRoom.setName(requestDto.getRoomName());
        meetingRoom.setType(requestDto.getType());
        if(requestDto.getLink() != null)
            meetingRoom.setLink(requestDto.getLink());
        return meetingRoomRepository.save(meetingRoom);
    }

    @Transactional
    public List<MeetingRoom> getMeetingRoomList(Mode mode){
        return meetingRoomRepository.findMeetingRoomList(mode);
    }

    @Transactional
    public void updateMeetingRoomEndStatus(int roomId)throws SomethingNotFoundException{
        meetingRoomRepository.updateMeetingRoomEndStatus(roomId);
    }

    @Transactional
    public void startRoom(int roomID) throws SomethingNotFoundException {
        meetingRoomRepository.startRoom(roomID);
    }

    @Transactional
    public void endRoom(int roomID) throws SomethingNotFoundException{
        meetingRoomRepository.endRoom(roomID);
    }

    @Transactional
    public boolean checkSecret(int roomID, String password){return meetingRoomRepository.checkSecret(roomID, password);}

    @Transactional
    public boolean checkWAIT(int roomID){return meetingRoomRepository.checkWAIT(roomID);}

}
