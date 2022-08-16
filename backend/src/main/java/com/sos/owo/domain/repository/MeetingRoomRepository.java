package com.sos.owo.domain.repository;

import com.sos.owo.domain.MeetingRoom;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.Mode;
import com.sos.owo.domain.RoomStatus;
import com.sos.owo.error.Exception.custom.SomethingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
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
        if(meetingRoom==null) throw new SomethingNotFoundException("meetingRoom(id:"+id+")");
        return meetingRoom;
    }

    public List<MeetingRoom> findMeetingRoomList(Mode mode) {
        List<MeetingRoom> meetingRoomList = em.createQuery("select m from MeetingRoom m where m.mode = :mode and m.status = :status ORDER BY m.openDate DESC", MeetingRoom.class)
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

    public void startRoom(int roomID) {
        MeetingRoom findRoom = (MeetingRoom)this.em.find(MeetingRoom.class, roomID);
        findRoom.setStatus(RoomStatus.START);
        findRoom.setStartDate(LocalDateTime.now());
        this.em.persist(findRoom);
        this.em.flush();
    }

    public void endRoom(int roomID) {
        MeetingRoom findRoom = (MeetingRoom)this.em.find(MeetingRoom.class, roomID);
        findRoom.setStatus(RoomStatus.END);
        findRoom.setEndDate(LocalDateTime.now());
        this.em.persist(findRoom);
        this.em.flush();
    }

    public boolean checkSecret(int roomID, String password) {
        MeetingRoom findRoom = (MeetingRoom)this.em.find(MeetingRoom.class, roomID);
        if (!findRoom.isSecret()) {
            return true;
        } else {
            return findRoom.getPassword().equals(password);
        }
    }

    public boolean checkWAIT(int roomID) {
        MeetingRoom findRoom = (MeetingRoom)this.em.find(MeetingRoom.class, roomID);
        return findRoom.getStatus() == RoomStatus.WAIT;
    }

}
