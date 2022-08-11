package com.sos.owo.dto;

import com.sos.owo.domain.RecordImg;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecordFileDto {

    private int id;
    private int record_id;
    private String fileOriName;
    private String fileName;
    private String fileUrl;

    public RecordImg toEntity(){
        RecordImg build = RecordImg.builder()
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

}
