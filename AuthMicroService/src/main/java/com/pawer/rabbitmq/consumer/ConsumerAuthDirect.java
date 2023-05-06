package com.pawer.rabbitmq.consumer;

import com.pawer.rabbitmq.messagemodel.ModelUpdateUser;
import com.pawer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerAuthDirect {
    private final AuthService authService;

    @RabbitListener(queues = "queue-update-user")
    public void listenerSaveDriver(ModelUpdateUser modelSave){
        authService.updateAuth(modelSave);
    }
}
