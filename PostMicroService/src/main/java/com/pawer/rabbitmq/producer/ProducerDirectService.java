package com.pawer.rabbitmq.producer;

import com.pawer.rabbitmq.messagemodel.ModelFindUsernameForCreateComment;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerDirectService {
    private final RabbitTemplate rabbitTemplate;
    public String sendfindUsernameForCreateComment(ModelFindUsernameForCreateComment model){
        System.out.println("producer girdi model ici.." +model.getToken());
       return (String) rabbitTemplate.convertSendAndReceive("exchange-direct","binding-key-create-comment",model);
            }
}
