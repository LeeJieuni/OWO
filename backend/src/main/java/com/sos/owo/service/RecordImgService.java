package com.sos.owo.service;

import com.sos.owo.domain.Record;
import com.sos.owo.domain.RecordImg;
import com.sos.owo.domain.repository.RecordImgRepository;
import com.sos.owo.domain.repository.RecordRepository;
import com.sos.owo.dto.FileDto;
import com.sos.owo.dto.RecordFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecordImgService {

    @Autowired
    private RecordImgRepository recordImgRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Transactional
    public RecordFileDto saveFile(int recordId, String fileOriName, String fileName, String fileUrl) throws IllegalStateException{
        Record findRecord = recordRepository.findOne(recordId);
        RecordFileDto fileDto = new RecordFileDto();
        fileDto.setFileName(fileOriName);
        fileDto.setFileOriName(fileName);
        fileDto.setFileUrl(fileUrl);
        RecordImg recordImg = fileDto.toEntity();
        if(findRecord.getRecordImg() == null){
            recordImgRepository.save(recordImg);
        } else{
            RecordImg findRecordImg = findRecord.getRecordImg();
            findRecordImg.updateRecordImg(recordImg);
        }
        findRecord.updateRecordImg(recordImg);

        return fileDto;
    }

    @Transactional
    public RecordFileDto getFile(int recordId){
        Record findRecord = recordRepository.findOne(recordId);
        RecordImg recordImg = findRecord.getRecordImg();
        if(recordImg == null) return null;
        RecordFileDto fileDto = RecordFileDto.builder()
                .id(recordImg.getId())
                .fileOriName(recordImg.getFileOriName())
                .fileName(recordImg.getFileName())
                .fileUrl(recordImg.getFileUrl())
                .build();
        return fileDto;
    }







}
