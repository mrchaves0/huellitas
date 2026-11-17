package com.example.huellitas.service;

import com.example.huellitas.exception.BusinessException;
import com.example.huellitas.model.*;
import com.example.huellitas.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final UsuarioRepository usuarioRepository;

    public ReporteService(ReporteRepository reporteRepository, UsuarioRepository usuarioRepository) {
        this.reporteRepository = reporteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Cambia el estado de un reporte. Sólo un usuario con rol ADMINISTRADOR puede aprobar o rechazar.
     */
    @Transactional
    public Reporte cambiarEstado(Long reporteId, ReporteEstado nuevoEstado, Usuario admin) {
        if (admin.getRol() != UsuarioRol.ADMINISTRADOR) {
            throw new BusinessException("Solo los administradores pueden cambiar el estado del reporte");
        }
        Reporte reporte = reporteRepository.findById(reporteId)
                .orElseThrow(() -> new BusinessException("Reporte no encontrado"));
        // Sólo permitimos pasar a APROBADO o RECHAZADO
        if (nuevoEstado != ReporteEstado.APROBADO && nuevoEstado != ReporteEstado.RECHAZADO) {
            throw new BusinessException("Estado inválido para aprobación/rechazo");
        }
        reporte.setEstado(nuevoEstado);
        return reporteRepository.save(reporte);
    }

    /**
     * Crea un nuevo reporte (cualquier usuario puede reportar).
     */
    @Transactional
    public Reporte crearReporte(Reporte reporte, Usuario reportante) {
        // Asociamos al reportante
        reporte.setReportante(reportante);
        reporte.setFecha(LocalDateTime.now());
        reporte.setEstado(ReporteEstado.PENDIENTE);
        return reporteRepository.save(reporte);
    }
}
