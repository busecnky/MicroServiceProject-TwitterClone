package com.pawer.rabbitmq.messagemodel;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelCreateCommentToPost implements Serializable {
    private String token;
    private String postId;
    private String comment;
}
