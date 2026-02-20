package com.vent_entra_sf.proyect_vent_sofi.service;

import com.vent_entra_sf.proyect_vent_sofi.dto.compra.CompraCreateDTO;
import com.vent_entra_sf.proyect_vent_sofi.dto.compra.CompraResponseDTO;

import java.util.List;

public interface ICompraService {
    //List<CompraResponseDTO> allCompras();
    CompraResponseDTO findCompra(Long idCompra);
    CompraResponseDTO createCompra(CompraCreateDTO dto);
}
