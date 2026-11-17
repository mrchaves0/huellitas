package com.example.huellitas.service;

import com.example.huellitas.exception.BusinessException;
import com.example.huellitas.model.*;
import com.example.huellitas.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SolicitudAdopcionService {

    private final SolicitudAdopcionRepository solicitudRepository;
    private final PerroRepository perroRepository;
    private final UsuarioRepository usuarioRepository;
    private final RevisionVeterinariaRepository revisionRepository;

    public SolicitudAdopcionService(SolicitudAdopcionRepository solicitudRepository,
                                    PerroRepository perroRepository,
                                    UsuarioRepository usuarioRepository,
                                    RevisionVeterinariaRepository revisionRepository) {
        this.solicitudRepository = solicitudRepository;
        this.perroRepository = perroRepository;
        this.usuarioRepository = usuarioRepository;
        this.revisionRepository = revisionRepository;
    }

    /**
     * Crea una solicitud de adopción. Sólo un ciudadano (CIUDADANO) puede solicitar.
     * Además, el perro debe estar en estado DISPONIBLE y debe tener al menos una revisión veterinaria.
     */
    @Transactional
    public SolicitudAdopcion crearSolicitud(Long perroId, Long adoptanteId, String motivo) {
        Usuario adoptante = usuarioRepository.findById(adoptanteId)
                .orElseThrow(() -> new BusinessException("Usuario adoptante no encontrado"));
        if (adoptante.getRol() != UsuarioRol.CIUDADANO) {
            throw new BusinessException("Sólo usuarios con rol CIUDADANO pueden solicitar adopción");
        }
        Perro perro = perroRepository.findById(perroId)
                .orElseThrow(() -> new BusinessException("Perro no encontrado"));
        // Regla: perro debe estar disponible
        if (perro.getEstado() != PerroEstado.DISPONIBLE) {
            throw new BusinessException("El perro no está disponible para adopción");
        }
        // Regla: debe haber al menos una revisión veterinaria
        boolean tieneRevision = revisionRepository.findAll()
                .stream()
                .anyMatch(r -> r.getPerro() != null && r.getPerro().getId().equals(perroId));
        if (!tieneRevision) {
            throw new BusinessException("El perro debe tener una revisión veterinaria antes de solicitar adopción");
        }
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setPerro(perro);
        solicitud.setAdoptante(adoptante);
        solicitud.setMotivo(motivo);
        solicitud.setFecha(LocalDateTime.now());
        solicitud.setEstado(SolicitudAdopcionEstado.PENDIENTE);
        return solicitudRepository.save(solicitud);
    }

    /**
     * Cambia el estado de la solicitud (aprobada/rechazada). Sólo puede hacerlo un ADMIN.
     */
    @Transactional
    public SolicitudAdopcion cambiarEstado(Long solicitudId, SolicitudAdopcionEstado nuevoEstado, Usuario admin) {
        if (admin.getRol() != UsuarioRol.ADMINISTRADOR) {
            throw new BusinessException("Solo administradores pueden cambiar el estado de la solicitud de adopción");
        }
        SolicitudAdopcion solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new BusinessException("Solicitud de adopción no encontrada"));
        // Sólo aceptamos los estados PENDIENTE -> APROBADA/RECHAZADA
        if (solicitud.getEstado() != SolicitudAdopcionEstado.PENDIENTE) {
            throw new BusinessException("Sólo se puede cambiar el estado de solicitudes en PENDIENTE");
        }
        if (nuevoEstado != SolicitudAdopcionEstado.APROBADA && nuevoEstado != SolicitudAdopcionEstado.RECHAZADA) {
            throw new BusinessException("Estado inválido para la solicitud de adopción");
        }
        solicitud.setEstado(nuevoEstado);
        return solicitudRepository.save(solicitud);
    }
}
