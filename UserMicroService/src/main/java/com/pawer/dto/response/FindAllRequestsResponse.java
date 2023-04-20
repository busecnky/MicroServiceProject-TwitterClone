package com.pawer.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FindAllRequestsResponse {
    private String name;
    private String surname;
    private String username;
    private String job;
    private String avatar;
    private Long followId;
    private Long followerId;

}
