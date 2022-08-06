package com.sos.owo.service;


import com.sos.owo.domain.Record;
import com.sos.owo.domain.repository.RecordRepository;
import com.sos.owo.dto.RecordDto;
import com.sos.owo.dto.RecordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    @Transactional
    public Record registRecord(int memberId, int meetingRoomId, Record record) {
        return recordRepository.registRecord(memberId,meetingRoomId,record);
    }

    @Transactional
    public RecordResponseDto findRecord(int recordId){
        return recordRepository.findOne(recordId);
    }

    @Transactional
    public List<RecordResponseDto> findRecordYesterday(int memberId){
        return recordRepository.findRecordYesterday(memberId);
    }
    @Transactional
    public List<RecordResponseDto> findRecordByDay(int memberId, LocalDate dateTime){
        return recordRepository.findRecordByDay(memberId,dateTime);
    }
    @Transactional
    public List<RecordResponseDto> findRecordByMonth(int memberId, int year, int month){
        return recordRepository.findRecordByMonth(memberId, year, month);
    }
    @Transactional
    public int findWeekSum(int memberId){
        return recordRepository.findWeekSum(memberId);
    }

    @Transactional
    public Map<String,Integer> findPercentage(int memberId){
        return recordRepository.findPercentage(memberId);
    }

    @Transactional
    public Map<String,Integer> findAchievementRate(int memberId){
        return recordRepository.findAchievementRate(memberId);
    }

    @Transactional
    public int findYesterdaySum(int memberId){
        return recordRepository.findYesterdaySum(memberId);
    }

    @Transactional
    public int findlastingDay(int memberId){
        return recordRepository.findlastingDay(memberId);
    }


}
