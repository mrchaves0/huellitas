package com.example.huellitas.service;

import com.example.huellitas.exception.BusinessException;
import com.example.huellitas.model.*;
import com.example.huellitas.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RescateService {

    private final RescateRepository rescateRepository;
    private final ReporteRepository reporteRepository;
    private final UsuarioRepository usuarioRepository;

    public RescateService(RescateRepository rescateRepository,
                         ReporteRepository reporteRepository,
                         UsuarioRepository usuarioRepository) {
        this.rescateRepository = rescateRepository;
        this.reporteRepository = reporteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Crea un rescate asignado a un voluntario tomando un reporte que ya esté APROBADO.
     */
    @Transactional
    public Rescate iniciarRescate(Long reporteId, Long voluntarioId) {
        Usuario voluntario = usuarioRepository.findById(voluntarioId)
                .orElseThrow(() -> new BusinessException("Voluntario no encontrado"));
        if (voluntario.getRol() != UsuarioRol.VOLUNTARIO) {
            throw new BusinessException("Solo usuarios con rol VOLUNTARIO pueden iniciar un rescate");
        }
        Reporte reporte = reporteRepository.findById(reporteId)
                .orElseThrow(() -> new BusinessException("Reporte no encontrado"));
        if (reporte.getEstado() != ReporteEstado.APROBADO) {
            throw new BusinessException("Solo reportes con estado APROBADO pueden ser rescatados");
        }
        Rescate rescate = new Rescate();
        rescate.setReporte(reporte);
        rescate.setVoluntario(voluntario);
        rescate.setFecha(LocalDateTime.now());
        rescate.setObservaciones("Rescate iniciado");
        rescate.setEstado(RescateEstado.PENDIENTE);
        return rescateRepository.save(rescate);
    }

    /**
     * Avanza el estado del rescate siguiendo la secuencia: PENDIENTE -> EN_PROCESO -> FINALIZADO.
     */
    @Transactional
    public Rescate avanzarEstado(Long rescateId, RescateEstado nuevoEstado) {
        Rescate rescate = rescateRepository.findById(rescateId)
                .orElseThrow(() -> new BusinessException("Rescate no encontrado"));
        RescateEstado actual = rescate.getEstado();
        // Validar transición correcta
        if (actual == RescateEstado.PENDIENTE && nuevoEstado != RescateEstado.EN_PROCESO) {
            throw new BusinessException("Transición inválida: PENDIENTE solo puede pasar a EN_PROCESO");
        }
        if (actual == RescateEstado.EN_PROCESO && nuevoEstado != RescateEstado.FINALIZADO) {
            throw new BusinessException("Transición inválida: EN_PROCESO solo puede pasar a FINALIZADO");
        }
        if (actual == RescateEstado.FINALIZADO) {
            throw new BusinessException("El rescate ya está FINALIZADO y no puede avanzar más");
        }
        rescate.setEstado(nuevoEstado);
        rescate.setObservaciones("Estado actualizado a " + nuevoEstado);
        return rescateRepository.save(rescate);
    }
}
