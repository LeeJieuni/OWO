package com.sos.owo.service;


import com.sos.owo.domain.Record;
import com.sos.owo.domain.repository.RecordRepository;
import com.sos.owo.dto.RecordTimeSumDto;
import com.sos.owo.dto.RecordResponseDto;
import com.sos.owo.error.Exception.custom.SomethingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    @Transactional
    public Record registRecord(int memberId, int meetingRoomId, int recordImgId,Record record) throws SomethingNotFoundException {
        return recordRepository.registRecord(memberId,meetingRoomId,recordImgId,record);
    }

    @Transactional
    public RecordResponseDto findRecord(int recordId) throws SomethingNotFoundException{
        return recordRepository.findOne(recordId);
    }

    @Transactional
    public List<RecordResponseDto> findRecordYesterday(int memberId) throws Exception{
        return recordRepository.findRecordYesterday(memberId);
    }
    @Transactional
    public List<RecordResponseDto> findRecordByDay(int memberId, LocalDate dateTime)throws SomethingNotFoundException{
        return recordRepository.findRecordByDay(memberId,dateTime);
    }
    @Transactional
    public List<RecordResponseDto> findRecordByMonth(int memberId, int year, int month)throws SomethingNotFoundException{
        return recordRepository.findRecordByMonth(memberId, year, month);
    }
    @Transactional
    public int findWeekSum(int memberId)throws SomethingNotFoundException{
        return recordRepository.findWeekSum(memberId);
    }

    @Transactional
    public Map<String,Integer> findPercentage(int memberId)throws SomethingNotFoundException{
        return recordRepository.findPercentage(memberId);
    }

    @Transactional
    public Map<String,Integer> findAchievementRate(int memberId){
        return recordRepository.findAchievementRate(memberId);
    }

    @Transactional
    public int findYesterdaySum(int memberId) throws SomethingNotFoundException{
        return recordRepository.findYesterdaySum(memberId);
    }

    @Transactional
    public int findlastingDay(int memberId) throws SomethingNotFoundException{
        return recordRepository.findlastingDay(memberId);
    }
    @Transactional
    public List<RecordTimeSumDto> findPlaceByMonth(int memberId, int year, int month) throws SomethingNotFoundException {
        return recordRepository.findPlaceByMonth(memberId, year, month);
    }

}
