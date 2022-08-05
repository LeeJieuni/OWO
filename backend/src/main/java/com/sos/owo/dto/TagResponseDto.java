package com.sos.owo.dto;

import com.sos.owo.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagResponseDto {
    private int tagId;
    private int recordId;
    private String tagContent;
}
