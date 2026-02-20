package com.vent_entra_sf.proyect_vent_sofi.repository;

import com.vent_entra_sf.proyect_vent_sofi.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra,Long> {
}
