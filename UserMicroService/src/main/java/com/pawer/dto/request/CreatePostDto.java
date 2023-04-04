package com.pawer.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreatePostDto {

    private String content;
    private String token;
}
