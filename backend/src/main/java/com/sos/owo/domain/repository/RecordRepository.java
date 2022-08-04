package com.sos.owo.domain.repository;

import com.sos.owo.domain.MeetingRoom;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.Record;
import com.sos.owo.dto.RecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    @PersistenceContext
    private final EntityManager em;

    public Record findOne(int id){
        Record findRecord = em.find(Record.class, id);
        return findRecord;
    }

    public void registRecord(int memberId, int meetingRoomId, Record record){
        Member findMember = em.find(Member.class,memberId);
        record.setMember(findMember);

        MeetingRoom findMeetingRoom = em.find(MeetingRoom.class,meetingRoomId);
        record.setMeetingRoom(findMeetingRoom);
        em.persist(record);
    }


}