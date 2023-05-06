package com.pawer.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(indexName = "post")
public class Post extends BaseEntity {

    @Id
    private String id;
    private Long userId;
    private String username;
    private String name;
    private String surname;
    private String content;
    private String url;
    private Integer likeCount;
    @Builder.Default
    private String date=LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    @Builder.Default
    private String  time= LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

}
