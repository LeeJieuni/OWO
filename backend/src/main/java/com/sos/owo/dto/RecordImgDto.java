package com.sos.owo.dto;

import com.sos.owo.domain.RecordImg;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RecordImgDto {
    //url을 String으로 받기 위한 dto
    private int id;
    private String fileOriName;
    private String fileUrl;

    public RecordImgDto toEntity(){
        RecordImgDto build = RecordImgDto.builder()
                .fileOriName(fileOriName)
                .fileUrl(fileUrl)
                .build();
        return build;
    }

    public RecordImgDto(String fileOriName, String fileUrl) {
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
    }
}
