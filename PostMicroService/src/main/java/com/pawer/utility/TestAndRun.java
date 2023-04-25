package com.pawer.utility;

import com.pawer.manager.IElasticServiceManager;
import com.pawer.mapper.IPostMapper;
import com.pawer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestAndRun {

    private final PostService postService;

    private final IElasticServiceManager elasticServiceManager;

    @PostConstruct
    public void init(){
        new Thread(()->{
            run();
        }).start();
    }

    public void run(){
        try{
            postService.findAll().forEach(x->{
                elasticServiceManager.getAllData(IPostMapper.INSTANCE.fromPost(x));
                    });

            System.out.println("İşlem sona erdi.");
        }catch (Exception exception){

        }
    }



}
