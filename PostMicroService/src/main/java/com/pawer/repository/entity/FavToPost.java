package com.pawer.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class FavToPost extends BaseEntity{
    @Id
    private String id;
    private String postId;
    private Long userId;
    private Boolean statement;
}
