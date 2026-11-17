package com.example.huellitas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/** DTO para crear o actualizar un Reporte */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteRequest {
    @NotBlank
    private String descripcion;
    @NotBlank
    private String ubicacionReferencia;
    @NotNull
    private Double latitud;
    @NotNull
    private Double longitud;
    @NotBlank
    private String fotografia; // URL
}

/** DTO para la respuesta de Reporte */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResponse {
    private Long id;
    private String descripcion;
    private String ubicacionReferencia;
    private Double latitud;
    private Double longitud;
    private String fotografia;
    private LocalDateTime fecha;
    private ReporteEstadoDto estado;
    private Long reportanteId;
}

/** Enum DTO para estado de Reporte */
public enum ReporteEstadoDto {
    PENDIENTE,
    APROBADO,
    RECHAZADO
}
