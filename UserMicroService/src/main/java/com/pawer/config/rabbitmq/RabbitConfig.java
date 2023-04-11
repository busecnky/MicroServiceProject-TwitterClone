package com.pawer.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // exchange
    private String exchangeDirect = "exchange-direct";
    private String exchangeFanout = "exchange-fanout";
    private String exchangeTopic = "exchange-topic";


    // Key
    private String bindingKeyCreateUser = "binding-key-create-user";
    private String bindingKeyCreatePost = "binding-key-create-post";
    private String bindingKeyUpdateUser = "binding-key-update-user";
    private String bindingKeyCreateCommentToPost= "binding-key-create-comment-to-post";
    private String bindingKeyLikePost= "binding-key-like-post";



    // Queu
    private String queueCreateUser = "queue-create-user";
    private String queueCreatePost = "queue-create-post";
    private String queueUpdateUser= "queue-update-user";
    private String queueCreateCommentToPost= "queue-create-comment-to-post";
    private String queueLikePost= "queue-like-post";




    /**
     * ---- Exchange ----
     */
    @Bean
    DirectExchange exchangeDirect() {
        return new DirectExchange(exchangeDirect);
    }

    @Bean
    FanoutExchange exchangeFanout() {
        return new FanoutExchange(exchangeFanout);
    }

    @Bean
    TopicExchange exchangeTopic() {
        return new TopicExchange(exchangeTopic);
    }




    /**
     * ---- Queu ----
     */
    @Bean
    Queue queueSaveUser() {
        return new Queue(queueCreateUser);
    }
    @Bean
    Queue queueCreatePost(){return new Queue(queueCreatePost);}
    @Bean
    Queue queueUpdateUser(){return new Queue(queueUpdateUser);}
    @Bean
    Queue queueCreateCommentToPost(){return new Queue(queueCreateCommentToPost);}

    @Bean
    Queue queueLikePost(){
        return new Queue(queueLikePost);
    }


    /**
     * ---- Binding ----
     */
    @Bean
    public Binding bindingCreateUser(final Queue queueSaveUser, final DirectExchange directExchange) {
        return BindingBuilder.bind(queueSaveUser).to(directExchange).with(bindingKeyCreateUser);
    }
    @Bean
    public Binding bindingCreatePost(final Queue queueCreatePost, final DirectExchange directExchange) {
        return BindingBuilder.bind(queueCreatePost).to(directExchange).with(bindingKeyCreatePost);
    }
    @Bean
    public Binding bindingUpdateUser(final Queue queueUpdateUser, final DirectExchange directExchange) {
        return BindingBuilder.bind(queueUpdateUser).to(directExchange).with(bindingKeyUpdateUser);
    }
    @Bean
    public Binding bindingCreateCommentToPost(final Queue queueCreateCommentToPost, final DirectExchange directExchange){
        return BindingBuilder.bind(queueCreateCommentToPost).to(directExchange).with(bindingKeyCreateCommentToPost);
    }

    @Bean
    public Binding bindingLikePost(final Queue queueLikePost, final DirectExchange directExchange){
        return BindingBuilder.bind(queueLikePost).to(directExchange).with(bindingKeyLikePost);
    }


}