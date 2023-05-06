package com.pawer.config;

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
    private String bindingKeyCreatePost = "binding-key-create-post";

    // Queue
    private String queueCreatePost = "queue-create-post";

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
     * ---- Queue ----
     */
    @Bean
    Queue queueCreatePost(){return new Queue(queueCreatePost);}

    /**
     * ---- Binding ----
     */

    @Bean
    public Binding bindingCreatePost(final Queue queueCreatePost, final DirectExchange directExchange) {
        return BindingBuilder.bind(queueCreatePost).to(directExchange).with(bindingKeyCreatePost);
    }
}