package com.pawer.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthRegisterRequestDto {
    @NotNull(message = "User name is required")
    private String username;
    private String name;
    private String surname;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$",
    message = "Password must be at least 8 characters, at least one uppercase and one lowercase letter, " +
            "and contain numbers and special characters")
    @NotNull(message = "Password cannot be empty")
    String password;
    String repassword;
    @Email(message = "Enter a valid email")
    String email;

}
