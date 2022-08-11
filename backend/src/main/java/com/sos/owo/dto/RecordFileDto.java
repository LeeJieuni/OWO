package com.sos.owo.dto;

import com.sos.owo.domain.RecordImg;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class RecordFileDto {

    private int id;
    private int record_id;
    private String fileOriName;
    private String fileName;
    private String fileUrl;

    public RecordFileDto toEntity(){
        RecordFileDto build = RecordFileDto.builder()
                .id(id)
                .fileOriName(fileOriName)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .build();
        return build;
    }

    @Builder
    public RecordFileDto(int id, String fileOriName, String fileName, String fileUrl){
        this.id = id;
        this.fileOriName = fileOriName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public RecordFileDto(int id, String fileOriName, String fileUrl) {
        this.id = id;
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
    }
}
