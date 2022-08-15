package com.sos.owo.service;

import com.sos.owo.domain.Record;
import com.sos.owo.domain.RecordImg;
import com.sos.owo.domain.repository.RecordImgRepository;
import com.sos.owo.domain.repository.RecordRepository;
import com.sos.owo.dto.FileDto;
import com.sos.owo.dto.RecordFileDto;
import com.sos.owo.dto.RecordImgDto;
import com.sos.owo.dto.RecordResponseDto;
import com.sos.owo.error.Exception.custom.SomethingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordImgService {

    @Autowired
    private RecordImgRepository recordImgRepository;

    @Autowired
    private RecordRepository recordRepository;

//    @Transactional
//    public RecordFileDto saveFile(int recordId, String fileOriName, String fileName, String fileUrl) throws IllegalStateException{
//        System.out.println(">>>>>>>"+fileOriName+" "+fileName+" "+fileUrl);
//
//        Record findRecord = recordRepository.findOneRecord(recordId);
//        RecordFileDto fileDto = new RecordFileDto();
//        fileDto.setFileName(fileOriName);
//        fileDto.setFileOriName(fileName);
//        fileDto.setFileUrl(fileUrl);
//        RecordImg recordImg = fileDto.toEntity();
//        System.out.println(">>>>>+"+recordImg.getFileName()+" "+recordImg.getFileUrl()+" "+recordImg.getFileOriName());
//        try{
//            RecordImg findRecordImg = findRecord.getRecordImg();
//            findRecordImg.updateRecordImg(recordImg);
//        } catch (NullPointerException n){
//            System.out.println("nullPointerException__");
//            recordImgRepository.save(recordImg);
//        }
////        if(findRecord.getRecordImg() == null){
////
////            recordImgRepository.save(recordImg);
////        } else{
////            RecordImg findRecordImg = findRecord.getRecordImg();
////            findRecordImg.updateRecordImg(recordImg);
////        }
//        findRecord.updateRecordImg(recordImg);
//
//        return fileDto;
//    }

    @Transactional
    public RecordFileDto getFile(int recordId){
        Record findRecord = recordRepository.findOneRecord(recordId);
        RecordImg recordImg = findRecord.getRecordImg();
        if(recordImg == null) return null;
        RecordFileDto fileDto = RecordFileDto.builder()
                .id(recordImg.getId())
                .fileOriName(recordImg.getFileOriName())
                .fileName(recordImg.getFileName())
                .fileUrl(recordImg.getFileUrl().toString())
                .build();
        return fileDto;
    }

//    @Transactional
//    //멤버의 하루 운동 기록 사진을 모두 불러온다.
//    public List<RecordFileDto> getFileDayList(int memberId,LocalDate date){
//
//        List<RecordResponseDto> list = recordRepository.findRecordByDay(memberId,date);
//        List<RecordFileDto> fileList = new ArrayList<>();
//
//        for (RecordResponseDto findRecord:list) {
//            int id = findRecord.getRecordId();
//            Record r = recordRepository.findOneRecord(id);
//            RecordImg recordImg = r.getRecordImg();
//            if(recordImg == null) return null;
//            RecordFileDto fileDto = RecordFileDto.builder()
//                    .id(recordImg.getId())
//                    .fileOriName(recordImg.getFileOriName())
//                    .fileName(recordImg.getFileName())
//                    .fileUrl(new String(recordImg.getFileUrl()))
//                    .build();
//            fileList.add(fileDto);
//        }
//        return fileList;
//    }

//    @Transactional
//    public List<RecordFileDto> getFileMonthList(int memberId,int year,int day){
//
//        List<RecordResponseDto> list = recordRepository.findRecordByMonth(memberId,year,day);
//        List<RecordFileDto> fileList = new ArrayList<>();
//
//        for (RecordResponseDto findRecord:list) {
//            int id = findRecord.getRecordId();
//            Record r = recordRepository.findOneRecord(id);
//            RecordImg recordImg = r.getRecordImg();
//            if(recordImg == null) return null;
//            RecordFileDto fileDto = RecordFileDto.builder()
//                    .id(recordImg.getId())
//                    .fileOriName(recordImg.getFileOriName())
//                    .fileName(recordImg.getFileName())
//                    .fileUrl(recordImg.getFileUrl().toString())
//                    .build();
//            fileList.add(fileDto);
//        }
//        return fileList;
//    }

    @Transactional
    // 저장할 때 한번에 되게 만든 것
    public int saveImg(RecordImgDto recordImgDto){
        return recordRepository.saveImg(recordImgDto);
    }

    @Transactional
    public RecordImg getImg(int recordId)throws SomethingNotFoundException{
        return recordRepository.getImg(recordId);
    }

    //멤버의 하루 운동 기록 사진을 모두 불러온다.
    @Transactional
    public List<RecordImgDto> getFileDayList(int memberId,LocalDate date) throws SomethingNotFoundException{
        return recordRepository.getFileDayList(memberId, date);
    }
    @Transactional
    public List<RecordImgDto> getFileMonthList(int memberId,int year,int day) throws SomethingNotFoundException {
        return recordRepository.getFileMonthList(memberId, year, day);
    }

    @Transactional
    public List<RecordImgDto> findImgForMainList()throws SomethingNotFoundException{//int year, int month
        return recordRepository.findImgForMainList();//year,month);
    }

}
