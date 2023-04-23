package com.pawer.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentToPostResponse {
    private String comment;
    private String postId;
    private String userId;
    private String username;

}
