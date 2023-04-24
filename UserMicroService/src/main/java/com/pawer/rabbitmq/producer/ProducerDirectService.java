package com.pawer.rabbitmq.producer;

import com.pawer.rabbitmq.messagemodel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerDirectService {
    private final RabbitTemplate rabbitTemplate;

    public void sendCreatePost(ModelCreatePost modelCreatePost){
        rabbitTemplate.convertAndSend("exchange-topic","binding-key-create-post-topic",modelCreatePost);
        rabbitTemplate.convertAndSend("exchange-topic","binding-key-create-post-topic-elastic",modelCreatePost);
    }

    public void sendUpdateUser(ModelUpdateUser model){
        rabbitTemplate.convertAndSend("exchange-direct","binding-key-update-user",model);
    }

    public void sendFollodId(ModelFollowId model){
        rabbitTemplate.convertAndSend("exchange-direct","binding-key-follow-posts",model);
    }


}