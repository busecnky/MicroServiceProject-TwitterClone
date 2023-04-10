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
    private Boolean responseForFollowRequest; // takip isteğini kabul etmek veya reddetmek için cevap
    private String username;

}
