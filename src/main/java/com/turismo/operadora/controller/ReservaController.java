package com.turismo.operadora.controller;

import com.turismo.operadora.config.RabbitMQConfig;
import com.turismo.operadora.dtos.ReservaEvent;
import com.turismo.operadora.dtos.ReservaRequest;
import com.turismo.operadora.entity.Reserva; // Asegúrate de importar la entidad Reserva
import com.turismo.operadora.servicios.ReservaProducer;
import com.turismo.operadora.servicios.ReservaService; // Importar ReservaService
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
// @CrossOrigin(origins = "http://localhost") // Puedes quitar esta línea si usas la configuración global en OperadoraApplication
public class ReservaController {

    private final ReservaProducer producer;
    private final ReservaService reservaService; // Declaración del campo final

    // Constructor que inyecta ambas dependencias
    public ReservaController(ReservaProducer producer, ReservaService reservaService) {
        this.producer = producer;
        this.reservaService = reservaService; // Inicialización del campo final
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> listar() {
        return ResponseEntity.ok(reservaService.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody ReservaRequest reserva) {
        producer.enviarEvento(new ReservaEvent("CREAR", reserva));
        producer.enviarEvento(RabbitMQConfig.QUEUE_NOTIFICACIONES, new ReservaEvent("NOTIFICACION_CREAR", reserva));
        producer.enviarEvento(RabbitMQConfig.QUEUE_LOGS, new ReservaEvent("LOG_CREAR", reserva));
        return ResponseEntity.ok("Evento de creación enviado.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody ReservaRequest reserva) {
        reserva.setId(id);
        producer.enviarEvento(new ReservaEvent("ACTUALIZAR", reserva));
        producer.enviarEvento(RabbitMQConfig.QUEUE_NOTIFICACIONES, new ReservaEvent("NOTIFICACION_ACTUALIZAR", reserva));
        producer.enviarEvento(RabbitMQConfig.QUEUE_LOGS, new ReservaEvent("LOG_ACTUALIZAR", reserva));
        return ResponseEntity.ok("Evento de actualización enviado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        ReservaRequest reserva = new ReservaRequest();
        reserva.setId(id);
        producer.enviarEvento(new ReservaEvent("ELIMINAR", reserva));
        producer.enviarEvento(RabbitMQConfig.QUEUE_NOTIFICACIONES, new ReservaEvent("NOTIFICACION_ELIMINAR", reserva));
        producer.enviarEvento(RabbitMQConfig.QUEUE_LOGS, new ReservaEvent("LOG_ELIMINAR", reserva));
        return ResponseEntity.ok("Evento de eliminación enviado.");
    }
}



