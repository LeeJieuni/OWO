package com.sos.owo.dto;

import com.sos.owo.domain.ProfileImg;
import com.sos.owo.domain.RecordImg;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
public class FileDto {
    private int id;
    private String fileOriName;
    private String fileName;
    private String fileUrl;


    public FileDto toEntity() {
        FileDto build = FileDto.builder()
                .id(id)
                .fileOriName(fileOriName)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .build();
        return build;
    }

    @Builder
    public FileDto(int id,String fileOriName,String fileName, String fileUrl) {
        this.id = id;
        this.fileOriName = fileOriName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
