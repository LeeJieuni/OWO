package com.sos.owo.domain.repository;

import com.sos.owo.domain.MeetingRoom;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.Record;
import com.sos.owo.domain.RecordImg;
import com.sos.owo.dto.*;
import com.sos.owo.error.Exception.custom.UserNotFoundException;
import com.sos.owo.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    @PersistenceContext
    private final EntityManager em;
    private final TagRepository tagRepository;
    private final GoalRepository goalRepository;


    public Record findOneRecord(int recordId){
        Record record = em.find(Record.class,recordId);
        return record;
    }
    //운동 기록 조회
    public RecordResponseDto findOne(int recordId){
        Record f = em.find(Record.class, recordId);

        List<TagResponseDto> tagList = tagRepository.findTagList(recordId);
        RecordResponseDto recordResponse = new RecordResponseDto(recordId, f.getMember().getId(),f.getMeetingRoom().getId(),tagList,
                f.getRecordTime(), f.getRecordImg().getId(),
                f.getRecordMemo() ,f.getRecordDatetime(),f.isRecordSecret(),f.getRecordExercise().toString(),f.getPlace());

        return recordResponse;
    }

    // 운동 기록을 저장한다.
    // member,미팅룸을 불러와서, 그것으로 정보를 저장하고,
    // 운동 시간, 운동종류(ex.AEROBIC) 저장
    public Record registRecord(int memberId, int meetingRoomId, int recordImgId,Record record){
        Member findMember = em.find(Member.class,memberId);
        record.setMember(findMember);
        MeetingRoom findMeetingRoom = em.find(MeetingRoom.class,meetingRoomId);
        record.setMeetingRoom(findMeetingRoom);

//        int hour = findMeetingRoom.getEndDate().getHour()-findMeetingRoom.getStartDate().getHour();
//        int endmin = findMeetingRoom.getEndDate().getMinute();
//        int startmin = findMeetingRoom.getStartDate().getMinute();
//        int min = 0;
//        if(endmin<startmin){
//            hour--;
//            min = 60-startmin+endmin;
//        }else{
//            min = endmin-startmin;
//        }
//        record.setRecordTime(hour*60 + min);


        record.setRecordExercise(findMeetingRoom.getType());

        RecordImg recordImg = em.find(RecordImg.class,recordImgId);
        record.setRecordImg(recordImg);

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
                    r.getRecordTime(),
                    r.getRecordImg().getId(),
                    r.getRecordMemo() ,r.getRecordDatetime(),r.isRecordSecret(),r.getRecordExercise().toString(),r.getPlace()));
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
                        r.getRecordTime(),
                        r.getRecordImg().getId(),
                        r.getRecordMemo() ,r.getRecordDatetime(),r.isRecordSecret(),r.getRecordExercise().toString(),r.getPlace()));
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
        Query query = em.createQuery("SELECT r.recordExercise ,count(r.recordTime) as c FROM Record as r WHERE r.member.id = :memberId and r.recordExercise not in ('GAME') GROUP BY r.recordExercise ORDER BY c DESC")
                .setParameter("memberId",memberId);
        List<Object[]> recordList = query.getResultList(); // 사용자에 대한 모든 운동 기록 리스트

        //한 사람에 대한 운동 기록 총 수 // count의 반환형은 Long이다.
        Long sum = em.createQuery("SELECT count(r.recordTime) FROM Record as r WHERE r.member.id = :memberId and r.recordExercise not in ('GAME')",Long.class)
                .setParameter("memberId",memberId).getSingleResult();

        Map<String,Integer> percentage = new LinkedHashMap<>();

        for (Object[] r:recordList) {
            System.out.println(">>>>>"+r[0].toString()+"  "+r[1]);
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


    public int saveImg(RecordImgDto recordImgDto){ //int recordId,
        System.out.println("2 "+recordImgDto.getId()+" "+recordImgDto.getFileOriName()+" "+recordImgDto.getFileUrl());
        RecordImg recordImg = new RecordImg();
        recordImg.setFileOriName(recordImgDto.getFileOriName());
//        recordImg.setFileName(recordId+"_"+recordImgDto.getFileOriName());
        recordImg.setFileUrl(recordImgDto.getFileUrl().getBytes());
        System.out.println("3 "+recordImg.getId()+" "+recordImg.getFileOriName()+" "+recordImg.getFileUrl());
        em.persist(recordImg);

//        Record record = em.find(Record.class,recordId);
//        record.setRecordImg(recordImg);
//        em.persist(record);
        return recordImg.getId();
    }

    public RecordImg getImg(int recordId) {
        Record record = em.find(Record.class,recordId);
        RecordImg recordImg = record.getRecordImg();
        return recordImg;
    }

    public List<RecordImgDto> getFileDayList(int memberId,LocalDate date){
        List<RecordResponseDto> list = findRecordByDay(memberId,date);
        List<RecordImgDto> responseList = new ArrayList<>();

        for (RecordResponseDto findRecord:list) {
            int recordId = findRecord.getRecordId();
            Record r = findOneRecord(recordId);
            RecordImg recordImg = r.getRecordImg();
            if(recordImg == null) return null;
            RecordImgDto recordImgDto = RecordImgDto.builder()
                    .id(recordImg.getId())
                    .fileOriName(recordImg.getFileOriName())
                    .fileUrl(new String(recordImg.getFileUrl()))
                    .build();
            responseList.add(recordImgDto);
        }
        return responseList;
    }

    public List<RecordImgDto> getFileMonthList(int memberId,int year,int day){
        List<RecordResponseDto> list = findRecordByMonth(memberId,year,day);
        List<RecordImgDto> responseList = new ArrayList<>();

        for (RecordResponseDto findRecord:list) {
            int recordId = findRecord.getRecordId();
            Record r = findOneRecord(recordId);
            RecordImg recordImg = r.getRecordImg();
            if(recordImg == null) return null;
            RecordImgDto recordImgDto = RecordImgDto.builder()
                    .id(recordImg.getId())
                    .fileOriName(recordImg.getFileOriName())
                    .fileUrl(new String(recordImg.getFileUrl()))
                    .build();
            responseList.add(recordImgDto);
        }
        return responseList;
    }

    public List<RecordPlaceDto> findPlaceByMonth(int memberId, int year, int month){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new UserNotFoundException(memberId);

        Query query = em.createQuery("SELECT r.recordDatetime, min(r.place), sum(r.recordTime) FROM Record as r WHERE r.member.id = :memberId group by r.recordDatetime")
                .setParameter("memberId",memberId); // 사용자에 대한 모든 운동 기록 리스트
        List<Object[]> recordList = query.getResultList();
        List<RecordPlaceDto> monthList = new ArrayList<>();

        for(Object[] r:recordList){
            String dateString = String.valueOf(r[0]);
            LocalDate date = LocalDate.parse(dateString);
            if (date.getYear()==year && date.getMonthValue() == month){
              monthList.add(new RecordPlaceDto(date,Integer.parseInt(String.valueOf(r[1])),Integer.parseInt(String.valueOf(r[2]))));
            }
        }

        return monthList;
    }
}