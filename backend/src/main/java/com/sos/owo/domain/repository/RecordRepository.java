package com.sos.owo.domain.repository;

import com.sos.owo.domain.MeetingRoom;
import com.sos.owo.domain.Member;
import com.sos.owo.domain.Record;
import com.sos.owo.domain.RecordImg;
import com.sos.owo.dto.*;
import com.sos.owo.error.Exception.custom.SomethingNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    @PersistenceContext
    private final EntityManager em;
    private final TagRepository tagRepository;
    private final GoalRepository goalRepository;
    private final MemberRepository memberRepository;

    public Record findOneRecord(int recordId){
        Record record = em.find(Record.class,recordId);
        if(record == null) throw new SomethingNotFoundException("record(id:"+recordId+")");
        return record;
    }
    //운동 기록 조회
    public RecordResponseDto findOne(int recordId){
        Record record = em.find(Record.class, recordId);
        /* 가록이 없다면 예외처리 */
        if(record == null) throw new SomethingNotFoundException("record(id:"+recordId+")");

        List<TagResponseDto> tagList = tagRepository.findTagList(recordId);
        RecordResponseDto recordResponse = new RecordResponseDto(recordId, record.getMember().getId(),record.getMeetingRoom().getId(),tagList,
                record.getRecordTime(), record.getRecordImg().getId(),
                record.getRecordMemo() ,record.getRecordDatetime(),record.isRecordSecret(),record.getRecordExercise().toString());

        return recordResponse;
    }

    public Record registRecord(int memberId, int meetingRoomId, int recordImgId,Record record){
        /* 사람, 미팅방, 사진에 대한 정보 모두 들어가야 하기때문에 빈값을 넣어주는 대신 그냥 예외처리함.*/
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");
        record.setMember(findMember);

        MeetingRoom findMeetingRoom = em.find(MeetingRoom.class,meetingRoomId);
        if(findMeetingRoom == null) throw new SomethingNotFoundException("meetingRoom(id:"+meetingRoomId+")");
        record.setMeetingRoom(findMeetingRoom);

        record.setRecordExercise(findMeetingRoom.getType());

        RecordImg recordImg = em.find(RecordImg.class,recordImgId);
        if(recordImg == null) throw new SomethingNotFoundException("recordImg(id:"+recordImgId+")");
        record.setRecordImg(recordImg);

        em.persist(record);

        return record;
    }



    public List<RecordResponseDto> findRecordByDay(int memberId, LocalDate dateTime){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        List<Record> recordList = em.createQuery("SELECT r FROM Record as r WHERE r.member.id = :memberId and r.recordDatetime = :dateTime",Record.class)
                .setParameter("memberId",memberId).setParameter("dateTime",dateTime).getResultList();
        List<RecordResponseDto> recordResponseDtoList = new ArrayList<>();
        if(!recordList.isEmpty()){ /* 값이 없으면 빈 리스트 반환 */
            for(Record r:recordList){
                List<TagResponseDto> tagList = tagRepository.findTagList(r.getRecordId());
                recordResponseDtoList.add(new RecordResponseDto(r.getRecordId(), r.getMember().getId(),r.getMeetingRoom().getId(),tagList,
                        r.getRecordTime(),
                        r.getRecordImg().getId(),
                        r.getRecordMemo() ,r.getRecordDatetime(),r.isRecordSecret(),r.getRecordExercise().toString()));
            }
        }

        return recordResponseDtoList;
    }

    public List<RecordResponseDto> findRecordYesterday(int memberId) throws Exception{
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return findRecordByDay(memberId,yesterday);
    }


    public List<RecordResponseDto> findRecordByMonth(int memberId, int year, int month){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        List<Record> recordList = em.createQuery("SELECT r FROM Record as r WHERE r.member.id = :memberId", Record.class)
                .setParameter("memberId",memberId).getResultList(); // 사용자에 대한 모든 운동 기록 리스트

        List<RecordResponseDto> monthList = new ArrayList<>();
        if(!recordList.isEmpty()) { /* 값이 없으면 빈 리스트 반환 */
            for (Record r : recordList) {
                if (r.getRecordDatetime().getYear() == year
                        && r.getRecordDatetime().getMonthValue() == month) {
                    List<TagResponseDto> tagList = tagRepository.findTagList(r.getRecordId());
                    monthList.add(new RecordResponseDto(r.getRecordId(), r.getMember().getId(), r.getMeetingRoom().getId(), tagList,
                            r.getRecordTime(),
                            r.getRecordImg().getId(),
                            r.getRecordMemo(), r.getRecordDatetime(), r.isRecordSecret(), r.getRecordExercise().toString()));
                }
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
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        List<Record> recordList = findWeekRecord(memberId);

        int sumTimeOfWeek = 0;
        if(!recordList.isEmpty()) {
            for (Record r : recordList) {
                sumTimeOfWeek += r.getRecordTime();
            }
        }
        return sumTimeOfWeek;
    }

    public Map<String,Integer> findPercentage(int memberId){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        Query query = em.createQuery("SELECT r.recordExercise ,count(r.recordTime) as c FROM Record as r WHERE r.member.id = :memberId and r.recordExercise not in ('GAME') GROUP BY r.recordExercise ORDER BY c DESC")
                .setParameter("memberId",memberId);
        List<Object[]> recordList = query.getResultList(); // 사용자에 대한 모든 운동 기록 리스트

        //한 사람에 대한 운동 기록 총 수 // count의 반환형은 Long이다.
        Long sum = em.createQuery("SELECT count(r.recordTime) FROM Record as r WHERE r.member.id = :memberId and r.recordExercise not in ('GAME')",Long.class)
                .setParameter("memberId",memberId).getSingleResult();

        Map<String,Integer> percentage = new LinkedHashMap<>();

        if(!recordList.isEmpty() && sum != 0) {
            for (Object[] r : recordList) {
                System.out.println(">>>>>" + r[0].toString() + "  " + r[1]);
                percentage.put(r[0].toString(), (int) ((double) Integer.parseInt(r[1].toString()) / sum * 100.0));
            }
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
        if(!goal.isEmpty()) {
            for (GoalResponseDto g : goal) {
                if(!recordList.isEmpty()) {
                    for (Object[] r : recordList) {
                        System.out.println(">>" + g.getExercise() + " " + r[0].toString());
                        if (g.getExercise() != r[0].toString()) {
                            continue;
                        }
                        double achieveHour = (double) Integer.parseInt(r[1].toString()) / 60; // 분단위라서 시간단위로 바꿔줌

                        // 운동종목, 성취시간/목표시간*100(백분율)
                        achievement.put(r[0].toString(), (int) (achieveHour / g.getHour() * 100.0));
                        break;
                    }
                }else{
                    achievement.put(g.getExercise(),0);
                }
            }
        }
        return achievement;
    }


    public int findYesterdaySum(int memberId){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        List<Long> score = em.createQuery("SELECT sum(r.recordTime) FROM Record as r WHERE (r.member.id = :memberId) AND (r.recordDatetime =:yesterday)")
                .setParameter("memberId",memberId)
                .setParameter("yesterday",yesterday)
                .getResultList();
        int yesterdaySum = 0;

        if(score.get(0) == null || score == null || score.size() == 0) return yesterdaySum;
        yesterdaySum = score.get(0).intValue();
        return yesterdaySum;
    }

    public int findlastingDay(int memberId){ //오늘 혹은 어제를 기준으로 연속되는 날짜 조회
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        LocalDate today = LocalDate.now();
        Query query = em.createQuery("SELECT DISTINCT r.recordDatetime FROM Record as r WHERE r.member.id = :memberId ORDER BY r.recordDatetime DESC")
                .setParameter("memberId",memberId);
        List<LocalDate> recordList = query.getResultList();
        int lastingDay = 0;
        boolean startYesterday = false;

        if(recordList.isEmpty())return 0;

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

        RecordImg recordImg = new RecordImg();

        recordImg.setFileOriName(recordImgDto.getFileOriName());
        recordImg.setFileUrl(recordImgDto.getFileUrl().getBytes());

        em.persist(recordImg);

        return recordImg.getId();
    }


    public RecordImg getImg(int recordId) {
        Record record = em.find(Record.class,recordId);
        if(record == null) throw new SomethingNotFoundException("record(id:"+recordId+")");
        RecordImg recordImg = record.getRecordImg();
        return recordImg;
    }

    public List<RecordImgDto> getFileDayList(int memberId,LocalDate date){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        List<RecordResponseDto> list = findRecordByDay(memberId,date);
        List<RecordImgDto> responseList = new ArrayList<>();

        if(!list.isEmpty()) {
            for (RecordResponseDto findRecord : list) {
                int recordId = findRecord.getRecordId();
                Record r = findOneRecord(recordId);
                RecordImg recordImg = r.getRecordImg();
                if (recordImg == null) return null;
                RecordImgDto recordImgDto = RecordImgDto.builder()
                        .id(recordImg.getId())
                        .fileOriName(recordImg.getFileOriName())
                        .fileUrl(new String(recordImg.getFileUrl()))
                        .build();
                responseList.add(recordImgDto);
            }
        }
        return responseList;
    }

    public List<RecordImgDto> getFileMonthList(int memberId,int year,int day){
        /* memberId에 대한 예외처리 생략 */
        List<RecordResponseDto> list = findRecordByMonth(memberId,year,day);
        List<RecordImgDto> responseList = new ArrayList<>();

        if(!list.isEmpty()) {
            for (RecordResponseDto findRecord : list) {
                int recordId = findRecord.getRecordId();
                Record r = findOneRecord(recordId);
                RecordImg recordImg = r.getRecordImg();
                if (recordImg == null) return null;
                RecordImgDto recordImgDto = RecordImgDto.builder()
                        .id(recordImg.getId())
                        .fileOriName(recordImg.getFileOriName())
                        .fileUrl(new String(recordImg.getFileUrl()))
                        .build();
                responseList.add(recordImgDto);
            }
        }
        return responseList;
    }

    public List<RecordTimeSumDto> findPlaceByMonth(int memberId, int year, int month){
        Member findMember = em.find(Member.class,memberId);
        if(findMember == null) throw new SomethingNotFoundException("member(id:"+memberId+")");

        Query query = em.createQuery("SELECT r.recordDatetime, sum(r.recordTime) FROM Record as r WHERE r.member.id = :memberId group by r.recordDatetime")
                .setParameter("memberId",memberId); // 사용자에 대한 모든 운동 기록 리스트
        List<Object[]> recordList = query.getResultList();
        List<RecordTimeSumDto> monthList = new ArrayList<>();

        if(!recordList.isEmpty()) {
            for (Object[] r : recordList) {
                String dateString = String.valueOf(r[0]);
                LocalDate date = LocalDate.parse(dateString);
                if (date.getYear() == year && date.getMonthValue() == month) {
                    monthList.add(new RecordTimeSumDto(date, Integer.parseInt(String.valueOf(r[1]))));
                }
            }
        }

        return monthList;
    }

    public List<RecordImgDto> findImgForMainList(){//int year,int month){
        List<Record> list = em.createQuery("SELECT r FROM Record as r WHERE r.recordSecret = false and r.recordExercise not in ('GAME') order by r.recordDatetime desc", Record.class)
               .getResultList(); // 사용자에 대한 모든 운동 기록 리스트

        List<RecordImgDto> responseList = new ArrayList<>();
        int count = 0;

        if(!list.isEmpty()) { /* 값이 없으면 빈 리스트 반환 */
            for (Record r : list) {
//                if (r.getRecordDatetime().getYear() == year
//                        && r.getRecordDatetime().getMonthValue() == month) {

                    if(!r.getRecordImg().getFileOriName().equals("")){
                        System.out.println(r.getRecordImg().getId()+" d  "+r.getRecordImg().getFileOriName()+"  dd "+r.getRecordImg().getFileUrl());
                        RecordImgDto recordImgDto = RecordImgDto.builder()
                                .id(r.getRecordImg().getId())
                                .fileOriName(r.getRecordImg().getFileOriName())
                                .fileUrl(new String(r.getRecordImg().getFileUrl())).build();
                        responseList.add(recordImgDto);
                        count++;
                        if(count>=10) break;
                    }
//                }
            }
        }

        return responseList;
    }
}