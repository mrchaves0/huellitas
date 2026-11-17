package com.example.huellitas.repository;

import com.example.huellitas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Puedes añadir métodos de búsqueda personalizados si los necesitas
}
