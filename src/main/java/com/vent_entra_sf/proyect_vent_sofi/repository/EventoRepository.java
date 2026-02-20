package com.vent_entra_sf.proyect_vent_sofi.repository;

import com.vent_entra_sf.proyect_vent_sofi.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento,Long> {
}
