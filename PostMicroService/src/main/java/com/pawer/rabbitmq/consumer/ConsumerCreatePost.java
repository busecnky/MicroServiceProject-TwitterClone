package com.pawer.rabbitmq.consumer;

import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerCreatePost {

    private final PostService postService;

    @RabbitListener(queues = "queue-create-post")
    public void createPostConsumerListener(ModelCreatePost modelCreatePost){
        postService.savePost(modelCreatePost);
    }

}
