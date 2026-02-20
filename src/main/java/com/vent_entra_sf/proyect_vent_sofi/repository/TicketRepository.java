package com.vent_entra_sf.proyect_vent_sofi.repository;

import com.vent_entra_sf.proyect_vent_sofi.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
