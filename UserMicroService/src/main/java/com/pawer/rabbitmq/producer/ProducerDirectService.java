package com.pawer.rabbitmq.producer;

import com.pawer.rabbitmq.messagemodel.ModelCreateCommentToPost;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.rabbitmq.messagemodel.ModelLikePost;
import com.pawer.rabbitmq.messagemodel.ModelUpdateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerDirectService {
    private final RabbitTemplate rabbitTemplate;

    public void sendCreatePost(ModelCreatePost modelCreatePost){
        rabbitTemplate.convertAndSend("exchange-direct","binding-key-create-post",modelCreatePost);
    }

    public void sendUpdateUser(ModelUpdateUser model){
        rabbitTemplate.convertAndSend("exchange-direct","binding-key-update-user",model);
    }

    public void sendCreateCommentToPost(ModelCreateCommentToPost model){
        rabbitTemplate.convertAndSend("exchange-direct","binding-key-create-comment-to-post",model);
    }

    public void sendLikePost(ModelLikePost model){
        rabbitTemplate.convertAndSend("exchange-direct","binding-key-like-post",model);
    }


}