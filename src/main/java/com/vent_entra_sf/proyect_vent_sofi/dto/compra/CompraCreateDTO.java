package com.vent_entra_sf.proyect_vent_sofi.dto.compra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CompraCreateDTO {
    private String nombre;
    private String apellido;
    private String email;
    private Long eventoId;
    private Integer cantidadEntradas;
}
