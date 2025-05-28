package com.turismo.operadora.servicios;

import com.turismo.operadora.entity.Reserva;
import com.turismo.operadora.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva>obtenerTodas() {
        return reservaRepository.findAll();
    }
}
