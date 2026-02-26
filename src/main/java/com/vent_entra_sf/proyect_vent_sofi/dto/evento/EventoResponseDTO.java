package com.vent_entra_sf.proyect_vent_sofi.dto.evento;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class EventoResponseDTO {
    private Long idEvento;
    private String nombre;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fecha;
    private String lugar;
    private BigDecimal precio;
}
