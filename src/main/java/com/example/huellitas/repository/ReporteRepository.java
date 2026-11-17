package com.example.huellitas.repository;

import com.example.huellitas.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    // Busca reportes por su estado (String)
    List<Reporte> findByEstado(String estado);

    // Reportes con coordenadas válidas (para el mapa)
    List<Reporte> findAllByLatitudIsNotNullAndLongitudIsNotNull();
}
