package com.vent_entra_sf.proyect_vent_sofi.controller;

import com.vent_entra_sf.proyect_vent_sofi.dto.compra.CompraCreateDTO;
import com.vent_entra_sf.proyect_vent_sofi.dto.compra.CompraResponseDTO;
import com.vent_entra_sf.proyect_vent_sofi.service.ICompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/compras")
public class CompraController {
    @Autowired
    private ICompraService compraService;

    @PostMapping
    public CompraResponseDTO createCompra(@RequestBody CompraCreateDTO dto){
        return compraService.createCompra(dto);
    }

    @GetMapping
    public String compraGet(){
        return "Esta funcionando todo OK";
    }
}
