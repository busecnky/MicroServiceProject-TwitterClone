package com.pawer.rabbitmq.messagemodel;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelFollowPosts {
    private List<Long> follodId;
}
