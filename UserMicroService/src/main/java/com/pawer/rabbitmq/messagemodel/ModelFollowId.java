package com.pawer.rabbitmq.messagemodel;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelFollowId implements Serializable {
    private List<Long> followId;
}
