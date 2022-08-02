package com.sos.owo.domain.repository;

import com.sos.owo.domain.MeetingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
@RequiredArgsConstructor
public class MeetingRoomRepository {

    @PersistenceContext
    private final EntityManager em;

    public int save(MeetingRoom meetingRoom){
        em.persist(meetingRoom);
        return meetingRoom.getId();
    }

    public MeetingRoom findOne(int id){
        MeetingRoom meetingRoom = em.find(MeetingRoom.class, id);
        return meetingRoom;
    }



}
