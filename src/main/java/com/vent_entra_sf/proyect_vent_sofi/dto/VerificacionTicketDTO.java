package com.vent_entra_sf.proyect_vent_sofi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class VerificacionTicketDTO {
    private String codigo;
    private String estadoPago;
    private String estadoTicket;
    private String comprador;
}
