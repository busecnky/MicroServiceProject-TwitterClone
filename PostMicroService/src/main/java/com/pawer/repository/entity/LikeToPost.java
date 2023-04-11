package com.pawer.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class LikeToPost extends BaseEntity {
    private String id;
    private String postId;
    private Long userId;
    private Boolean statement;
}
