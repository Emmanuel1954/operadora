package com.turismo.operadora.servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turismo.operadora.config.RabbitMQConfig;
import com.turismo.operadora.dtos.ReservaEvent;
import com.turismo.operadora.dtos.ReservaRequest;
import com.turismo.operadora.entity.Reserva;
import com.turismo.operadora.repository.ReservaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservaConsumer {

    private final ReservaRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    public ReservaConsumer(ReservaRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void procesarEvento(ReservaEvent event) {
        try {
            ReservaRequest datos = event.getDatos();
            switch (event.getAccion()) {
                case "CREAR" -> {
                    repository.save(new Reserva(datos.getCliente(), datos.getTour(), datos.getFecha()));
                    System.out.println(" Reserva creada");
                }
                case "ACTUALIZAR" -> {
                    repository.findById(datos.getId()).ifPresentOrElse(
                            r -> {
                                r.setCliente(datos.getCliente());
                                r.setTour(datos.getTour());
                                r.setFecha(datos.getFecha());
                                repository.save(r);
                                System.out.println(" Reserva actualizada");
                            },
                            () -> System.out.println(" Reserva no encontrada para actualizar")
                    );
                }
                case "ELIMINAR" -> {
                    repository.deleteById(datos.getId());
                    System.out.println("🗑️ Reserva eliminada");
                }
                default -> System.out.println(" Acción no reconocida: " + event.getAccion());
            }
        } catch (Exception e) {
            System.err.println(" Error al procesar evento: " + e.getMessage());
        }
    }}
