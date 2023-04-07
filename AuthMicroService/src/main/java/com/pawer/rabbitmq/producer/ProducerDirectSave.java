package com.pawer.rabbitmq.producer;

import com.pawer.rabbitmq.messagemodel.ModelUserSave;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerDirectSave {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessageSaveUser(ModelUserSave modelSave){
        rabbitTemplate.convertAndSend("exchange-direct","binding-key-create-user",modelSave);
    }

}
