package com.turismo.operadora.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE = "cola_reservas";


    @Bean
    public Queue queue() {
        return new Queue(QUEUE,false);
    }
}
