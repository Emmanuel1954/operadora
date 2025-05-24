package com.turismo.operadora.servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turismo.operadora.config.RabbitMQConfig;
import com.turismo.operadora.dtos.ReservaEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReservaProducer {

    private final RabbitTemplate rabbitTemplate;

    public ReservaProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarEvento(ReservaEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE, event);
        System.out.println(" Evento enviado: " + event.getAccion());
    }
}
