package com.pawer.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "commenttopost")
public class CommentToPost extends BaseEntity {
    private String id;
    private String postId;
    private Long userId;
    private String comment;
}
