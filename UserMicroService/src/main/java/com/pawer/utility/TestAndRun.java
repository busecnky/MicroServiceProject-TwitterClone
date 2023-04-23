package com.pawer.utility;

import com.pawer.rabbitmq.messagemodel.ModelFollowPosts;
import com.pawer.rabbitmq.producer.ProducerDirectService;
import com.pawer.service.FollowService;
import com.pawer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestAndRun {
    private final ProducerDirectService producerDirectService;
    private final FollowService followService;

    //@PostConstruct
    public void init(){
        new Thread(()->{
            sendFollowPostsMessage();
        }).start();
    }
    public void sendFollowPostsMessage(){
        ModelFollowPosts followId= new ModelFollowPosts();
        //List <Long> followIdList = followService.findOptionalFollowList();
    }

}
