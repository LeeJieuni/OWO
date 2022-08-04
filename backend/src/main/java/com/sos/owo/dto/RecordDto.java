package com.sos.owo.dto;

import com.sos.owo.domain.Record;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class RecordDto {

    private int recordHour;
    private String recordMemo;
    private LocalDateTime recordDatetime;
    private boolean secret;

    @Builder
    public RecordDto(int recordHour, String recordMemo, LocalDateTime recordDatetime, boolean secret) {
        this.recordHour = recordHour;
        this.recordMemo = recordMemo;
        this.recordDatetime = recordDatetime;
        this.secret = secret;
    }

    public Record toEntity()
    {
        return Record.builder().recordHour(recordHour).recordMemo(recordMemo).recordDatetime(recordDatetime).record_secret(secret).build();
    }

}
