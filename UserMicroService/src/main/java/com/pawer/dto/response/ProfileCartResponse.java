package com.pawer.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileCartResponse {
    private String name;
    private String surname;
    private String username;
    private String job;
    private Integer postCount;
    private String avatar;
    private String follow;
    private String follower;


}
