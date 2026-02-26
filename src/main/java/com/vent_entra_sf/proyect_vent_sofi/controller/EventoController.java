package com.vent_entra_sf.proyect_vent_sofi.controller;

import com.vent_entra_sf.proyect_vent_sofi.dto.evento.EventoCreateDTO;
import com.vent_entra_sf.proyect_vent_sofi.dto.evento.EventoResponseDTO;
import com.vent_entra_sf.proyect_vent_sofi.service.IEventoServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evento")
public class EventoController {

    @Autowired
    private IEventoServie eventoServie;

    @PostMapping
    public void createEvento(@RequestBody EventoCreateDTO dto){
        eventoServie.createEvento(dto);
    }
    @PutMapping("/{idEvento}")
    public ResponseEntity<String> editarEvento(
            @PathVariable Long idEvento,
            @RequestBody EventoCreateDTO dto) {

        eventoServie.editEvento(idEvento, dto);

        return ResponseEntity.ok("Evento actualizado correctamente");
    }
}
