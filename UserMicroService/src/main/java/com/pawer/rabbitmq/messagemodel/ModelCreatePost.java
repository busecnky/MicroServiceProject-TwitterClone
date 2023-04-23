package com.pawer.rabbitmq.messagemodel;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelCreatePost implements Serializable {

    private String username;
    private String name;
    private String surname;
    private String content;
    private String token;
    //private MultipartFile image;
}
