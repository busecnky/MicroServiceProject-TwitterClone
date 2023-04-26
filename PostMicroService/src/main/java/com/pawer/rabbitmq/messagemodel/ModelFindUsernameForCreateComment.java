package com.pawer.rabbitmq.messagemodel;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelFindUsernameForCreateComment implements Serializable {
    String token;
}
