package com.vent_entra_sf.proyect_vent_sofi.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class TicketResponseDTO {
    private String codigo;
    private String estado;
}
