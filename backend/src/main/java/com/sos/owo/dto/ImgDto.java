package com.sos.owo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImgDto {
    private int id;
    private String fileOriName;
    private String fileName;
    private String fileUrl;

    public ImgDto toEntity() {
        ImgDto build = ImgDto.builder()
                .id(id)
                .fileOriName(fileOriName)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .build();
        return build;
    }

    @Builder
    public ImgDto(int id, String fileOriName, String fileName, String fileUrl) {
        this.id = id;
        this.fileOriName = fileOriName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
