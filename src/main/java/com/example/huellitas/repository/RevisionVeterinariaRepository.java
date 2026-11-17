package com.example.huellitas.repository;

import com.example.huellitas.model.RevisionVeterinaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevisionVeterinariaRepository extends JpaRepository<RevisionVeterinaria, Long> {
    // Métodos personalizados pueden añadirse aquí
}
