package com.pawer.rabbitmq.messagemodel;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelUpdateUser implements Serializable {
    private Long authId;
    private String name;
    private String surname;
    private String age;
}
