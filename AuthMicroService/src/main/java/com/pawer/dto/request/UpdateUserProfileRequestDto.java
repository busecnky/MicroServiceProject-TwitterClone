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
    private String email;



}
