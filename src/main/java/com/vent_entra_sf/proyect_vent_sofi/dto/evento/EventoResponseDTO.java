package com.vent_entra_sf.proyect_vent_sofi.dto.evento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class EventoResponseDTO {
    private Long idEvento;
    private String nombre;
    private LocalDateTime fecha;
    private String lugar;
    private BigDecimal precio;
}
