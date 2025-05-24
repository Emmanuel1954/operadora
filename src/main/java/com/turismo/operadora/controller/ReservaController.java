package com.turismo.operadora.controller;

import com.turismo.operadora.dtos.ReservaEvent;
import com.turismo.operadora.dtos.ReservaRequest;
import com.turismo.operadora.servicios.ReservaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaProducer producer;

    public ReservaController(ReservaProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody ReservaRequest reserva) {
        producer.enviarEvento(new ReservaEvent("CREAR", reserva));
        return ResponseEntity.ok("Evento de creación enviado.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody ReservaRequest reserva) {
        reserva.setId(id);
        producer.enviarEvento(new ReservaEvent("ACTUALIZAR", reserva));
        return ResponseEntity.ok("Evento de actualización enviado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        ReservaRequest reserva = new ReservaRequest();
        reserva.setId(id);
        producer.enviarEvento(new ReservaEvent("ELIMINAR", reserva));
        return ResponseEntity.ok("Evento de eliminación enviado.");
    }
}
