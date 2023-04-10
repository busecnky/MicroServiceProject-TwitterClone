package com.pawer.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateUserProfileRequestDto {

    private String name;
    private String surname;
    private String username;
    private String age;
    private String token;
    private String avatar;




}
