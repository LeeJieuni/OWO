package com.sos.owo.domain.repository;

import com.sos.owo.domain.MeetingRoom;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.Record;
import com.sos.owo.dto.RecordDto;
import com.sos.owo.dto.RecordResponseDto;
import com.sos.owo.dto.TagResponseDto;
import com.sos.owo.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    @PersistenceContext
    private final EntityManager em;
    private final TagRepository tagRepository;

    //운동 기록 조회
    public RecordResponseDto findOne(int recordId){
        Record f = em.find(Record.class, recordId);

        List<TagResponseDto> tagList = tagRepository.findTagList(recordId);
        RecordResponseDto recordResponse = new RecordResponseDto(recordId, f.getMember().getId(),f.getMeetingRoom().getId(),tagList,
                f.getRecordHour(),// f.getRecordImg().getId(),
                f.getRecordMemo() ,f.getRecordDatetime(),f.isRecordSecret(),f.getRecordExercise().toString());

        return recordResponse;
    }

    // 운동 기록을 저장한다.
    // member,미팅룸을 불러와서, 그것으로 정보를 저장하고,
    // 운동 시간, 운동종류(ex.AEROBIC) 저장
    public Record registRecord(int memberId, int meetingRoomId, Record record){
        Member findMember = em.find(Member.class,memberId);
        record.setMember(findMember);
        MeetingRoom findMeetingRoom = em.find(MeetingRoom.class,meetingRoomId);
        record.setMeetingRoom(findMeetingRoom);

        int hour = findMeetingRoom.getEndDate().getHour()-findMeetingRoom.getStartDate().getHour();
        int endmin = findMeetingRoom.getEndDate().getMinute();
        int startmin = findMeetingRoom.getStartDate().getMinute();
        int min = 0;
        if(endmin<startmin){
            hour--;
            min = 60-startmin+endmin;
        }else{
            min = endmin-startmin;
        }
        record.setRecordHour(hour*60 + min);

        record.setRecordExercise(findMeetingRoom.getType());

        em.persist(record);

        return record;
    }

    public List<RecordResponseDto> findRecordByDay(int memberId, LocalDate dateTime){
        System.out.println(dateTime);
        List<Record> recordList = em.createQuery("SELECT r FROM Record as r WHERE r.member.id = :memberId and r.recordDatetime = :dateTime",Record.class)
                .setParameter("memberId",memberId).setParameter("dateTime",dateTime).getResultList();

        List<RecordResponseDto> recordResponseDtoList = new ArrayList<>();
        for(Record r:recordList){
            List<TagResponseDto> tagList = tagRepository.findTagList(r.getRecordId());
            recordResponseDtoList.add(new RecordResponseDto(r.getRecordId(), r.getMember().getId(),r.getMeetingRoom().getId(),tagList,
                    r.getRecordHour(),// f.getRecordImg().getId(),
                    r.getRecordMemo() ,r.getRecordDatetime(),r.isRecordSecret(),r.getRecordExercise().toString()));
        }

        return recordResponseDtoList;
    }

}