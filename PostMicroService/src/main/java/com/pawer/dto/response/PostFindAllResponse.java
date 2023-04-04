package com.pawer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostFindAllResponse {

    private Long userId;
    private String username;
    private String name;
    private String surname;
    private String content;
    @Builder.Default
    private LocalDate date=LocalDate.now();
}
