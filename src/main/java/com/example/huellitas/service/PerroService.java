package com.example.huellitas.service;

import com.example.huellitas.exception.BusinessException;
import com.example.huellitas.model.*;
import com.example.huellitas.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerroService {

    private final PerroRepository perroRepository;
    private final RevisionVeterinariaRepository revisionRepository;

    public PerroService(PerroRepository perroRepository, RevisionVeterinariaRepository revisionRepository) {
        this.perroRepository = perroRepository;
        this.revisionRepository = revisionRepository;
    }

    /**
     * Guarda o actualiza un perro. Si se intenta establecer el estado DISPONIBLE,
     * se verifica que exista al menos una revisión veterinaria asociada.
     */
    @Transactional
    public Perro guardarPerro(Perro perro) {
        // Si el estado está a DISPONIBLE, validar revisión previa
        if (perro.getEstado() == PerroEstado.DISPONIBLE) {
            validarRevisionExistente(perro.getId());
        }
        return perroRepository.save(perro);
    }

    /**
     * Cambia el estado del perro a DISPONIBLE después de validar que tenga revisión.
     */
    @Transactional
    public Perro publicarPerro(Long perroId) {
        Perro perro = perroRepository.findById(perroId)
                .orElseThrow(() -> new BusinessException("Perro no encontrado"));
        validarRevisionExistente(perroId);
        perro.setEstado(PerroEstado.DISPONIBLE);
        return perroRepository.save(perro);
    }

    private void validarRevisionExistente(Long perroId) {
        if (perroId == null) {
            // Perro aún no tiene ID, no puede estar publicado
            throw new BusinessException("El perro debe existir antes de publicarse");
        }
        List<RevisionVeterinaria> revisiones = revisionRepository.findAll()
                .stream()
                .filter(r -> r.getPerro() != null && r.getPerro().getId().equals(perroId))
                .toList();
        if (revisiones.isEmpty()) {
            throw new BusinessException("El perro debe tener al menos una revisión veterinaria antes de publicarse");
        }
    }
}
