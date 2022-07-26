package com.sos.owo.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MemberSloganDto {
    int id;
    String slogan;
}
