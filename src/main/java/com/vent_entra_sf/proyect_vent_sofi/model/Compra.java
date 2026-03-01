package com.vent_entra_sf.proyect_vent_sofi.model;

import jakarta.persistence.*;
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
@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;

    private String nombreComprador;
    private String apellidoComprador;

    private String emailComprador;

    @Enumerated(EnumType.STRING)
    private EstadoCompra estadoCompra;

    private LocalDateTime fechaCompra;
    private BigDecimal total;

    @OneToMany(mappedBy = "compra",cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
