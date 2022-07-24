package com.sos.owo.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberLoginResponseDto {
    String email;
    String accessToken;
    String refreshToken;
}
