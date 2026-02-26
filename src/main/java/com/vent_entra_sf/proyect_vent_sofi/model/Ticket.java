package com.vent_entra_sf.proyect_vent_sofi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    private String codigoVerificacion;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private EstadoTicket estadoTicket;

    @ManyToOne
    private Evento evento;

    @ManyToOne
    private Compra compra;
}
