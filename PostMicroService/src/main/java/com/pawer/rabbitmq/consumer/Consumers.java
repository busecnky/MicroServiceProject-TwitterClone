package com.pawer.rabbitmq.consumer;

import com.pawer.StaticValue;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.rabbitmq.messagemodel.ModelFollowId;
import com.pawer.repository.entity.Post;
import com.pawer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Consumers {

    private final PostService postService;


    @RabbitListener(queues = "queue-create-post-topic")
    public void createPostConsumerListener(ModelCreatePost modelCreatePost) throws IOException {
        postService.savePost(modelCreatePost);
    }


    @RabbitListener(queues = "queue-follow-posts")
    public void followIdList (ModelFollowId model){
        postService.homePagePosts(model);
        StaticValue.modelFollowId = model;

    }


}
