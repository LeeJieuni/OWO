package com.sos.owo.domain.repository;

import com.sos.owo.domain.MeetingRoom;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.Record;
import com.sos.owo.dto.GoalResponseDto;
import com.sos.owo.dto.RecordDto;
import com.sos.owo.dto.RecordResponseDto;
import com.sos.owo.dto.TagResponseDto;
import com.sos.owo.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    @PersistenceContext
    private final EntityManager em;
    private final TagRepository tagRepository;
    private final GoalRepository goalRepository;
    //운동 기록 조회
    public RecordResponseDto findOne(int recordId){
        Record f = em.find(Record.class, recordId);

        List<TagResponseDto> tagList = tagRepository.findTagList(recordId);
        RecordResponseDto recordResponse = new RecordResponseDto(recordId, f.getMember().getId(),f.getMeetingRoom().getId(),tagList,
                f.getRecordTime(),// f.getRecordImg().getId(),
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
        record.setRecordTime(hour*60 + min);

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
                    r.getRecordTime(),// f.getRecordImg().getId(),
                    r.getRecordMemo() ,r.getRecordDatetime(),r.isRecordSecret(),r.getRecordExercise().toString()));
        }

        return recordResponseDtoList;
    }

    public List<RecordResponseDto> findRecordYesterday(int memberId){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return findRecordByDay(memberId,yesterday);
    }


    public List<RecordResponseDto> findRecordByMonth(int memberId, int year, int month){
        List<Record> recordList = em.createQuery("SELECT r FROM Record as r WHERE r.member.id = :memberId", Record.class)
                .setParameter("memberId",memberId).getResultList(); // 사용자에 대한 모든 운동 기록 리스트

        List<RecordResponseDto> monthList = new ArrayList<>();
        System.out.println(recordList.get(0).getRecordDatetime().getMonthValue());
        for(Record r:recordList){
            if (r.getRecordDatetime().getYear() == year
                    && r.getRecordDatetime().getMonthValue() == month){
                List<TagResponseDto> tagList = tagRepository.findTagList(r.getRecordId());
                monthList.add(new RecordResponseDto(r.getRecordId(), r.getMember().getId(),r.getMeetingRoom().getId(),tagList,
                        r.getRecordTime(),// f.getRecordImg().getId(),
                        r.getRecordMemo() ,r.getRecordDatetime(),r.isRecordSecret(),r.getRecordExercise().toString()));
            }
        }

        return monthList;
    }

    public List<Record> findWeekRecord(int memberId){
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        LocalDate firstDayOfWeek = today.with(dayOfWeek.MONDAY);

        List<Record> recordList = em.createQuery("SELECT r FROM Record as r WHERE (r.member.id = :memberId) AND (r.recordDatetime BETWEEN :firstDay AND :today)",Record.class)
                .setParameter("memberId",memberId).setParameter("firstDay",firstDayOfWeek)
                .setParameter("today",today).getResultList();
        return recordList;
    }
    public int findWeekSum(int memberId){
        List<Record> recordList = findWeekRecord(memberId);

        int sumTimeOfWeek = 0;

        for (Record r:recordList) {
            sumTimeOfWeek += r.getRecordTime();
        }
        return sumTimeOfWeek;
    }

    public Map<String,Integer> findPercentage(int memberId){
        Query query = em.createQuery("SELECT r.recordExercise ,count(*) FROM Record as r WHERE r.member.id = :memberId GROUP BY r.recordExercise ")
                .setParameter("memberId",memberId);
        List<Object[]> recordList = query.getResultList(); // 사용자에 대한 모든 운동 기록 리스트

        //한 사람에 대한 운동 기록 총 수 // count의 반환형은 Long이다.
        Long sum = em.createQuery("SELECT count(*) FROM Record as r WHERE r.member.id = :memberId",Long.class)
                .setParameter("memberId",memberId).getSingleResult();

        HashMap<String,Integer> percentage = new HashMap<>();

        for (Object[] r:recordList) {
            System.out.println(">>>>>"+r);
            percentage.put(r[0].toString(),(int)((double)Integer.parseInt(r[1].toString()) / sum * 100.0));
        }
        return percentage;
    }

    public Map<String,Integer> findAchievementRate(int memberId){
        List<GoalResponseDto> goal = goalRepository.findGoal(memberId);

        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        LocalDate firstDayOfWeek = today.with(dayOfWeek.MONDAY);

        Query query = em.createQuery("SELECT r.recordExercise ,sum(r.recordTime) FROM Record as r WHERE (r.member.id = :memberId) AND (r.recordDatetime BETWEEN :firstDay AND :today) GROUP BY r.recordExercise ")
                .setParameter("memberId",memberId)
                .setParameter("firstDay",firstDayOfWeek)
                .setParameter("today",today);
        List<Object[]> recordList = query.getResultList(); // 사용자에 대한 일주일 운동 기록 리스트

        HashMap<String,Integer> achievement = new HashMap<>();

        for (GoalResponseDto g:goal) {
            for (Object[] r:recordList) {
                System.out.println(">>"+ g.getExercise() +" "+ r[0].toString());
                if(g.getExercise() != r[0].toString()){
                    continue;
                }
                double achieveHour = (double)Integer.parseInt(r[1].toString())/60; // 분단위라서 시간단위로 바꿔줌

                // 운동종목, 성취시간/목표시간*100(백분율)
                achievement.put(r[0].toString(),(int)(achieveHour / g.getHour() * 100.0));
                break;
            }
        }
        return achievement;
    }


    public int findYesterdaySum(int memberId){
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        List<Long> score = em.createQuery("SELECT sum(r.recordTime) FROM Record as r WHERE (r.member.id = :memberId) AND (r.recordDatetime =:yesterday)")
                .setParameter("memberId",memberId)
                .setParameter("yesterday",yesterday)
                .getResultList();
        int yesterdaySum = 0;

        if(score.get(0) == null || score == null || score.size() == 0) return yesterdaySum;
        System.out.println(score.get(0));
        yesterdaySum = score.get(0).intValue();
        return yesterdaySum;
    }

    public int findlastingDay(int memberId){ //오늘 혹은 어제를 기준으로 연속되는 날짜 조회
        LocalDate today = LocalDate.now();
        Query query = em.createQuery("SELECT DISTINCT r.recordDatetime FROM Record as r WHERE r.member.id = :memberId ORDER BY r.recordDatetime DESC")
                .setParameter("memberId",memberId);
        List<LocalDate> recordList = query.getResultList();
        int lastingDay = 0;
        boolean startYesterday = false;

        //오늘의 기록은 없고 어제의 기록부터 계산해야할 경우
        if(recordList.get(0).isEqual(today.minusDays(1))){
            lastingDay++;
            startYesterday=true;
        }
        for (int i=0;i<recordList.size();i++){
            if(!recordList.get(i).isEqual(today.minusDays(lastingDay))) break;
            lastingDay++;
        }
        if(startYesterday) lastingDay--;

        return lastingDay;
    }


}