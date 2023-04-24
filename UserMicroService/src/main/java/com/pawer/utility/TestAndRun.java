package com.pawer.utility;

import com.pawer.rabbitmq.messagemodel.ModelFollowId;
import com.pawer.rabbitmq.producer.ProducerDirectService;
import com.pawer.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        ModelFollowId followId= new ModelFollowId();
        //List <Long> followIdList = followService.findOptionalFollowList();
    }

}
