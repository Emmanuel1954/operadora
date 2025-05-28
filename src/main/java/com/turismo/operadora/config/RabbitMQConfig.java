package com.turismo.operadora.config;



import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {


    public static final String QUEUE_RESERVAS = "cola.reservas"; // Renombrado para mayor claridad
    public static final String QUEUE_NOTIFICACIONES = "cola.notificaciones"; // Nueva cola
    public static final String QUEUE_LOGS = "cola.logs"; // Otra nueva cola


    @Bean
    public Queue reservasQueue() {
        return new Queue(QUEUE_RESERVAS, false);
    }


    @Bean
    public Queue notificacionesQueue() {
        return new Queue(QUEUE_NOTIFICACIONES, false);
    }


    @Bean
    public Queue logsQueue() {
        return new Queue(QUEUE_LOGS, false);
    }


    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}

