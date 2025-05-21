package com.turismo.operadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turismo.operadora.entity.Reserva;
public interface ReservaRepository extends JpaRepository<Reserva, int> {

}
