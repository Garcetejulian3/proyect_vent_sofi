package com.vent_entra_sf.proyect_vent_sofi.controller;

import com.vent_entra_sf.proyect_vent_sofi.dto.ticket.TicketResponseDTO;
import com.vent_entra_sf.proyect_vent_sofi.model.Compra;
import com.vent_entra_sf.proyect_vent_sofi.repository.CompraRepository;
import com.vent_entra_sf.proyect_vent_sofi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketsController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private CompraRepository compraRepository;

    @GetMapping("/{compraId}/tickets")
    public ResponseEntity<List<TicketResponseDTO>> obtenerTickets(@PathVariable Long compraId) {

        Compra compra = compraRepository.findById(compraId)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        List<TicketResponseDTO> response = compra.getTickets()
                .stream()
                .map(ticket -> {
                    TicketResponseDTO dto = new TicketResponseDTO();
                    dto.setCodigo(ticket.getCodigoVerificacion());
                    dto.setEstado(ticket.getEstadoTicket().name());
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(response);
    }
}
