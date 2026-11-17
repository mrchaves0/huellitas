package com.example.huellitas.repository;

import com.example.huellitas.model.SolicitudAdopcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudAdopcionRepository extends JpaRepository<SolicitudAdopcion, Long> {
    // Puedes agregar consultas personalizadas aquí
}
