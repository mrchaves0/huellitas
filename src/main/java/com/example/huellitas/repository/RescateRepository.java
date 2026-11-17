package com.example.huellitas.repository;

import com.example.huellitas.model.Rescate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RescateRepository extends JpaRepository<Rescate, Long> {
    // Puedes añadir consultas personalizadas aquí
}
