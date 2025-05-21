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

public class ReservaProducer {
    @Service
    public class ReservaConsumer {

        private final ReservaRepository repository;
        private final ObjectMapper mapper = new ObjectMapper();

        public ReservaConsumer(ReservaRepository repository) {
            this.repository = repository;
        }

        @RabbitListener(queues = RabbitMQConfig.QUEUE)
        public void procesarEvento(String mensaje) {
            try {
                ReservaEvent event = mapper.readValue(mensaje, ReservaEvent.class);
                ReservaRequest datos = event.getDatos();

                switch (event.getAccion()) {
                    case "CREAR":
                        repository.save(new Reserva(datos.getCliente(), datos.getTour(), datos.getFecha()));
                        System.out.println("✅ Reserva creada");
                        break;
                    case "ACTUALIZAR":
                        Optional<Reserva> reservaOpt = repository.findById(datos.getId());
                        if (reservaOpt.isPresent()) {
                            Reserva r = reservaOpt.get();
                            r.setCliente(datos.getCliente());
                            r.setTour(datos.getTour());
                            r.setFecha(datos.getFecha());
                            repository.save(r);
                            System.out.println("✅ Reserva actualizada");
                        } else {
                            System.out.println("⚠️ Reserva no encontrada para actualizar");
                        }
                        break;
                    case "ELIMINAR":
                        repository.deleteById(datos.getId());
                        System.out.println("🗑️ Reserva eliminada");
                        break;
                    default:
                        System.out.println("❓ Acción no reconocida");
                }

            } catch (Exception e) {
                System.err.println("❌ Error al procesar evento: " + e.getMessage());
            }
        }
    }
}
