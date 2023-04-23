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
    private String bindingKeyUpdateUser = "binding-key-update-user";
    private String bindingKeyCreatePostTopic ="binding-key-create-post-topic";
    private String bindingKeyCreatePostTopicElastic ="binding-key-create-post-topic-elastic";
    private String bindingKeyFollowPosts = "binding-key-follow-posts";




    // Queu
    private String queueCreateUser = "queue-create-user";
    private String queueUpdateUser= "queue-update-user";
    private String queueCreatePostTopic="queue-create-post-topic";
    private String queueCreatePostTopicElastic="queue-create-post-topic-elastic";
    private String queueFollowPosts ="queue-follow-posts";







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
    Queue queueUpdateUser(){return new Queue(queueUpdateUser);}
    @Bean
    Queue queueCreatePostTopic(){
        return new Queue(queueCreatePostTopic);
    }
    @Bean
    Queue queueCreatePostTopicElastic(){
        return new Queue(queueCreatePostTopicElastic);
    }

    @Bean
    Queue queueFollowPosts(){return new Queue(queueFollowPosts);};

    /**
     * ---- Binding ----
     */
    @Bean
    public Binding bindingCreateUser(final Queue queueSaveUser, final DirectExchange directExchange) {
        return BindingBuilder.bind(queueSaveUser).to(directExchange).with(bindingKeyCreateUser);
    }

    @Bean
    public Binding bindingUpdateUser(final Queue queueUpdateUser, final DirectExchange directExchange) {
        return BindingBuilder.bind(queueUpdateUser).to(directExchange).with(bindingKeyUpdateUser);
    }

    @Bean
    public Binding bindingCreatePostTopic(final Queue queueCreatePostTopic, final TopicExchange topicExchange){
        return BindingBuilder.bind(queueCreatePostTopic).to(topicExchange).with(bindingKeyCreatePostTopic);
    }
    @Bean
    public Binding bindingCreatePostTopicElastic(final Queue queueCreatePostTopicElastic, final TopicExchange topicExchange){
        return BindingBuilder.bind(queueCreatePostTopicElastic).to(topicExchange).with(bindingKeyCreatePostTopicElastic);
    }

    @Bean
    public Binding bindingFollowPosts(final Queue queueFollowPosts,final DirectExchange directExchange){
        return BindingBuilder.bind(queueFollowPosts).to(directExchange).with(bindingKeyFollowPosts);
    }



}