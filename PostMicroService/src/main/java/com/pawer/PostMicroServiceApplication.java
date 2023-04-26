package com.pawer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;



@SpringBootApplication
@EnableFeignClients
public class PostMicroServiceApplication {
    public static void main(String[] args)  {
        SpringApplication.run(PostMicroServiceApplication.class);
    }




}