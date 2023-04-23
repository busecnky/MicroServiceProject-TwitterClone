package com.pawer.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreatePostDto {
    private String token;
    private String content;
    //private MultipartFile image;

}
