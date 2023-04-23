package com.pawer.rabbitmq.consumer;

import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.repository.entity.Post;
import com.pawer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ConsumerCreatePost {

    private final PostService postService;

    @RabbitListener(queues = "queue-create-post-topic")
    public void createPostConsumerListener(ModelCreatePost modelCreatePost) throws IOException {
        postService.savePost(modelCreatePost);
    }

    @RabbitListener(queues = "queue-get-post")
    public Iterable<Post> getPostsForElastic () {
        System.out.println("postmicro service consumer ici:... ");
        Iterable<Post> posts= postService.findAll();
        System.out.println("***************************");
        System.out.println(posts.toString());
        return posts;
    }


}
