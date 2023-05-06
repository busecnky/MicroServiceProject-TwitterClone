package com.pawer.rabbitmq.consumer;

import com.pawer.rabbitmq.messagemodel.ModelFindUsernameForCreateComment;
import com.pawer.rabbitmq.messagemodel.ModelUserSave;
import com.pawer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerUserSave {

    private final UserService userService;
    @RabbitListener(queues = "queue-create-user")
    public void listenerSaveDriver(ModelUserSave modelUserSave){
        userService.createUser(modelUserSave);
    }

    @RabbitListener(queues = "queue-create-comment")
    public String listenerCreateComment(ModelFindUsernameForCreateComment model){
        System.out.println("user microda consumer model ici.. "+model.getToken());
       return userService.findUsernameForComment(model.getToken());
    }
}
