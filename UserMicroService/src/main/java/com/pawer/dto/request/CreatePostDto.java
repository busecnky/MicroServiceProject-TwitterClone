package com.pawer.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreatePostDto {

    private String content;
    private String token;
    private MultipartFile image;
}
