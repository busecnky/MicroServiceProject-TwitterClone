package com.pawer.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseRequestDto {
    private String token;
    private String username;

}
