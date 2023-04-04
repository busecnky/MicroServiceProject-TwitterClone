package com.pawer.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentToPostDto {

    private String postId;
}
