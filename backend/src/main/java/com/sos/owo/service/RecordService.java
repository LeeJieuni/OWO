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
    public List<RecordResponseDto> findRecordByDay(int memberId, LocalDate dateTime){
        return recordRepository.findRecordByDay(memberId,dateTime);
    }
}
