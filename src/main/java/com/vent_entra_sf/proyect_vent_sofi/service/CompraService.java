package com.vent_entra_sf.proyect_vent_sofi.service;

import com.vent_entra_sf.proyect_vent_sofi.dto.compra.CompraCreateDTO;
import com.vent_entra_sf.proyect_vent_sofi.dto.compra.CompraResponseDTO;
import com.vent_entra_sf.proyect_vent_sofi.model.*;
import com.vent_entra_sf.proyect_vent_sofi.repository.CompraRepository;
import com.vent_entra_sf.proyect_vent_sofi.repository.EventoRepository;
import com.vent_entra_sf.proyect_vent_sofi.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CompraService implements ICompraService{
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CompraRepository compraRepository;

    /*@Override
    public List<CompraResponseDTO> allCompras() {
        List<Compra> compraList = compraRepository.findAll();
        List<CompraResponseDTO> dtoList = new ArrayList<>();

        for(Compra compra:compraList){
            CompraResponseDTO dto = new CompraResponseDTO();
            dto.setCompraId(compra.getIdCompra());
            dto.setEstado(compra.getEstadoCompra());
        }
        return List.of();
    }*/

    @Override
    public CompraResponseDTO findCompra(Long idCompra) {

        return null;
    }

    @Override
    public CompraResponseDTO createCompra(CompraCreateDTO dto) {
        // BUsco el evento en la base de datos antes de guardar la compra
        Evento evento = eventoRepository.findById(1L)
                .orElseThrow(()-> new RuntimeException("Evento no existente"));
        // Calculo el total
        BigDecimal total = evento.getPrecio()
                .multiply(BigDecimal.valueOf(dto.getCantidadEntradas()));
        // Genero la compra
        Compra compra = new Compra();
        compra.setNombreComprador(dto.getNombre());
        compra.setApellidoComprador(dto.getApellido());
        compra.setEmailComprador(dto.getEmail());
        compra.setEstadoCompra(EstadoCompra.PENDIENTE);
        compra.setFechaCompra(LocalDateTime.now());
        compra.setTotal(total);

        // Genero los tickets
        List<Ticket> tickets = new ArrayList<>();
        for (int i=0; i < dto.getCantidadEntradas(); i++){
            Ticket ticket = new Ticket();
            ticket.setCodigoVerificacion(UUID.randomUUID().toString());
            ticket.setEstadoTicket(EstadoTicket.VALIDO);
            ticket.setEvento(evento);
            ticket.setCompra(compra);

            tickets.add(ticket);
        }

        compra.setTickets(tickets);
        // Guardar todo
        compraRepository.save(compra);
        // Respuesta DTO
        CompraResponseDTO response = new CompraResponseDTO();
        response.setCompraId(compra.getIdCompra());
        response.setEstado(compra.getEstadoCompra().name());
        response.setTotal(compra.getTotal());

        return response;
    }
}
