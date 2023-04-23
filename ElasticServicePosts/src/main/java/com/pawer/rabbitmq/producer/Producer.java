package com.pawer.rabbitmq.producer;

import com.pawer.repository.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {
    private final RabbitTemplate rabbitTemplate;

    public Iterable<Post> sendGetData(){
        System.out.println("elastic producer send mesaj ici ..... ");
        return (Iterable<Post>) rabbitTemplate.convertSendAndReceive("exchange-direct","binding-key-get-post");
    }
}
