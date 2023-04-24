package com.pawer.utility;

import com.pawer.rabbitmq.producer.Producer;
import com.pawer.repository.entity.Post;
import com.pawer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestAndRun {
    private final PostService postService;
    private final Producer producer;

    @PostConstruct
    public void init(){
        new Thread(()->{
           getData();
        }).start();
    }
    public void getData(){

    }
}
