package com.pawer.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavPostRequestDto {
    private String token;
    private String postId;
    private Boolean statement;

}
