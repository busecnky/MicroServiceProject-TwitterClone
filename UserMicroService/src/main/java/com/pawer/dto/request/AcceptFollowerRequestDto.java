package com.pawer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcceptFollowerRequestDto {

    String token;
    // takip isteğini kabul etmek veya reddetmek için cevap
    private Boolean responseForFollowRequest;
    private String username;

}
