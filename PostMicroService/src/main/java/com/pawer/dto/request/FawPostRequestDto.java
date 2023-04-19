package com.pawer.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FawPostRequestDto {
    private String token;
    private String postId;
    private Boolean statement;

}
