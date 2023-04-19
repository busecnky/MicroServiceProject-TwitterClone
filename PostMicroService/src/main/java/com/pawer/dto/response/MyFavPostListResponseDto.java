package com.pawer.dto.response;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyFavPostListResponseDto {
    @Id
    private String id;
    private Long userId;
    private String username;
    private String name;
    private String surname;
    private String content;
    //private List<CommentToPost> posts;
    private String url;
    private Boolean isLiked;
    private Boolean isFav;
    private Integer likeCount;
    @Builder.Default
    private String date= LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    @Builder.Default
    private String  time= LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

}
