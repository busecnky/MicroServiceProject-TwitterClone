package com.pawer.rabbitmq.consumer;

import com.pawer.rabbitmq.messagemodel.ModelCreateCommentToPost;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ConsumerCreatePost {

    private final PostService postService;

    @RabbitListener(queues = "queue-create-post")
    public void createPostConsumerListener(ModelCreatePost modelCreatePost) throws IOException {
        postService.savePost(modelCreatePost);
    }
    @RabbitListener(queues = "queue-create-comment-to-post")
    public void createCommentToPost(ModelCreateCommentToPost model){postService.createCommentToPost(model);};

}
