package com.pawer.rabbitmq.consumer;

import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.rabbitmq.messagemodel.ModelFollowId;
import com.pawer.repository.entity.Post;
import com.pawer.service.PostService;
import com.pawer.utility.StaticValues;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Consumers {

    private final PostService postService;


    @RabbitListener(queues = "queue-create-post-topic")
    public void createPostConsumerListener(ModelCreatePost modelCreatePost) throws IOException {
        postService.savePost(modelCreatePost);
    }

    @RabbitListener(queues = "queue-follow-id-list")
    public void follodIdList (ModelFollowId model){
        System.out.println(model.getFollodId().toString());

        StaticValues.modelFollowId=model;
    }


}
