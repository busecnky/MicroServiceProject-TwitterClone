package com.pawer.rabbitmq.messagemodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelLikePost implements Serializable {

    private String token;
    private String postId;
    private Boolean statement;

}
