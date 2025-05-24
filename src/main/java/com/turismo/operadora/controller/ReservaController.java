package com.turismo.operadora.controller;

import com.turismo.operadora.dtos.ReservaEvent;
import com.turismo.operadora.dtos.ReservaRequest;
import com.turismo.operadora.entity.Reserva;
import com.turismo.operadora.repository.ReservaRepository;
import com.turismo.operadora.servicios.ReservaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin; // ¡Importa esto!

import java.util.List;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen. Para producción, sé más específico.
public class ReservaController {

    private final ReservaProducer producer;
    private final ReservaRepository reservaRepository;

    public ReservaController(ReservaProducer producer, ReservaRepository reservaRepository) { // Modifica el constructor
        this.producer = producer;
        this.reservaRepository = reservaRepository;
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody ReservaRequest reserva) {
        producer.enviarEvento(new ReservaEvent("CREAR", reserva));
        return ResponseEntity.ok("Evento de creación enviado a RabbitMQ.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody ReservaRequest reserva) {
        reserva.setId(id); // Asegúrate de que el ID de la ruta se asigne al objeto
        producer.enviarEvento(new ReservaEvent("ACTUALIZAR", reserva));
        return ResponseEntity.ok("Evento de actualización enviado a RabbitMQ.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        ReservaRequest reserva = new ReservaRequest(); // Crea un objeto DTO solo con el ID para la eliminación
        reserva.setId(id);
        producer.enviarEvento(new ReservaEvent("ELIMINAR", reserva));
        return ResponseEntity.ok("Evento de eliminación enviado a RabbitMQ.");
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodasLasReservas() {
        List<Reserva> reservas = reservaRepository.findAll();
        return ResponseEntity.ok(reservas);
    }

}
