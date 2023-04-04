package com.pawer.rabbitmq.consumer;


import com.pawer.rabbitmq.messagemodel.ModelSave;
import com.pawer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerUserSave {
    private final UserService userService;

    @RabbitListener(queues = "queue-create-user")
    public void listenerSaveDriver(ModelSave modelSave){
        userService.createUser(modelSave);
    }
}
