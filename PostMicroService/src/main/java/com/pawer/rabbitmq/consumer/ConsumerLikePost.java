package com.pawer.rabbitmq.consumer;

import com.pawer.rabbitmq.messagemodel.ModelLikePost;
import com.pawer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerLikePost {
    private final PostService postService;

    @RabbitListener(queues= "queue-like-post")
    public void createlikePost(ModelLikePost model){
        postService.createLikePost(model);
    }


}
