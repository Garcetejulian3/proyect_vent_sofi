package com.vent_entra_sf.proyect_vent_sofi.service;

import com.vent_entra_sf.proyect_vent_sofi.dto.evento.EventoCreateDTO;
import com.vent_entra_sf.proyect_vent_sofi.dto.evento.EventoResponseDTO;
import com.vent_entra_sf.proyect_vent_sofi.model.Evento;
import com.vent_entra_sf.proyect_vent_sofi.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventoService implements IEventoServie{
    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public EventoResponseDTO findEvento(Long idEvento) {
        EventoResponseDTO dto = new EventoResponseDTO();
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(()-> new RuntimeException("Evento no existente"));
        dto.setIdEvento(evento.getIdEvento());
        dto.setNombre(evento.getNombre());
        dto.setFecha(evento.getFecha());
        dto.setLugar(evento.getLugar());
        dto.setPrecio(evento.getPrecio());
        return dto;
    }

    @Override
    public List<EventoResponseDTO> allEventos() {
        List<Evento> eventoList = eventoRepository.findAll();
        List<EventoResponseDTO> listDto = new ArrayList<>();

        for (Evento evento:eventoList){
            EventoResponseDTO dto = new EventoResponseDTO();
            dto.setIdEvento(evento.getIdEvento());
            dto.setNombre(evento.getNombre());
            dto.setFecha(evento.getFecha());
            dto.setLugar(evento.getLugar());
            dto.setPrecio(evento.getPrecio());
            listDto.add(dto);
        }
        return listDto;
    }

    @Override
    public void createEvento(EventoCreateDTO dto) {
        Evento evento = new Evento();
        evento.setNombre(dto.getNombre());
        evento.setFecha(dto.getFecha());
        evento.setLugar(dto.getLugar());
        evento.setPrecio(dto.getPrecio());
        eventoRepository.save(evento);

    }

    @Override
    public void editEvento(Long idEvento, EventoCreateDTO dto) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento no existente"));

        evento.setNombre(dto.getNombre());
        evento.setFecha(dto.getFecha());
        evento.setLugar(dto.getLugar());
        evento.setPrecio(dto.getPrecio());

        eventoRepository.save(evento);
    }
}
