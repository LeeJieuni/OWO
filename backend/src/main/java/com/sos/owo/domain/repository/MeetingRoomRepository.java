package com.sos.owo.domain.repository;

import com.sos.owo.domain.MeetingRoom;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.Mode;
import com.sos.owo.domain.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.time.LocalDateTime;

@Transactional
@RequiredArgsConstructor
@Repository
public class MeetingRoomRepository {

    @PersistenceContext
    private final EntityManager em;

    public int save(MeetingRoom meetingRoom){
        meetingRoom.setOpenDate(LocalDateTime.now());
        em.persist(meetingRoom);
        return meetingRoom.getId();
    }

    public MeetingRoom findOne(int id){
        MeetingRoom meetingRoom = em.find(MeetingRoom.class, id);
        return meetingRoom;
    }

    public List<MeetingRoom> findMeetingRoomList(Mode mode) {
        List<MeetingRoom> meetingRoomList = em.createQuery("select m from MeetingRoom m where m.mode = :mode and m.status = :status", MeetingRoom.class)
                .setParameter("mode",mode)
                .setParameter("status", RoomStatus.WAIT)
                .getResultList();
        if(meetingRoomList.size() == 0) return null;
        return meetingRoomList;
    }

    public void updateMeetingRoomEndStatus(int roomId){
        MeetingRoom meetingRoom = findOne(roomId);
        meetingRoom.updateMeetingRoomStatus(RoomStatus.END);
        em.persist(meetingRoom);
        em.flush();
    }

    public void startRoom(int roomID){
        MeetingRoom findRoom = em.find(MeetingRoom.class, roomID);
        findRoom.setStatus(RoomStatus.START);
        findRoom.setStartDate(LocalDateTime.now());
        em.persist(findRoom);
        em.flush();
    }

    public void endRoom(int roomID){
        MeetingRoom findRoom = em.find(MeetingRoom.class, roomID);
        findRoom.setStatus(RoomStatus.END);
        findRoom.setEndDate(LocalDateTime.now());
        em.persist(findRoom);
        em.flush();
    }

}
