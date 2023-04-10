package com.pawer.rabbitmq.messagemodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelUserSave implements Serializable {
    private String username;
    private String name;
    private String surname;
    private String email;
    private Long authId;
}
