package com.example.huellitas.repository;

import com.example.huellitas.model.Perro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerroRepository extends JpaRepository<Perro, Long> {
    // Métodos de consulta personalizados pueden agregarse aquí
}
