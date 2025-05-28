package com.turismo.operadora.servicios;

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


    // Modificado para permitir especificar la cola
    public void enviarEvento(String queueName, ReservaEvent event) {
        rabbitTemplate.convertAndSend(queueName, event);
        System.out.println("Evento enviado a la cola '" + queueName + "': " + event.getAccion());
    }


    // Método original para compatibilidad, envía a la cola por defecto
    public void enviarEvento(ReservaEvent event) {
        enviarEvento(RabbitMQConfig.QUEUE_RESERVAS, event);
    }
}

