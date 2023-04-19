package com.pawer.rabbitmq.consumer;

import com.pawer.rabbitmq.messagemodel.ModelFindLikePost;
import com.pawer.rabbitmq.messagemodel.ModelLikePost;
import com.pawer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerPostLikePost {
    private final PostService postService;

//    @RabbitListener(queues= "queue-find-like-post")
//    public void createFindlikePost(ModelFindLikePost model){
//         postService.findAll(model);
//    }


}
