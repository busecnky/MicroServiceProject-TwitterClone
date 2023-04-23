package com.pawer.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentToPostRequestDto {

    private String token;
    private String postId;
    private String comment;
}
