package com.pawer.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChangePasswordDto {
    private String token;
    private String oldpassword;
    private String newpassword;
    private String confirmpassword;
}