package com.vent_entra_sf.proyect_vent_sofi.service;

import com.vent_entra_sf.proyect_vent_sofi.dto.evento.EventoCreateDTO;
import com.vent_entra_sf.proyect_vent_sofi.dto.evento.EventoResponseDTO;

import java.util.List;

public interface IEventoServie {
    EventoResponseDTO findEvento(Long idEvento);
    List<EventoResponseDTO> allEventos();
    void createEvento(EventoCreateDTO dto);
    void editEvento(Long idEvento,EventoCreateDTO dto);
}
