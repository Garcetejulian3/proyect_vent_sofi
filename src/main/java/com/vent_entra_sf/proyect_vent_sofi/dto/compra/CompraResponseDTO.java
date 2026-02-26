package com.vent_entra_sf.proyect_vent_sofi.dto.compra;

import com.vent_entra_sf.proyect_vent_sofi.model.EstadoCompra;
import com.vent_entra_sf.proyect_vent_sofi.model.Ticket;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CompraResponseDTO {
    private Long compraId;
    private String estado;
    private BigDecimal total;
    private String checkoutUrl;
}
