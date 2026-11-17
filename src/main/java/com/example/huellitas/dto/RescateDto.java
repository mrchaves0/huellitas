package com.example.huellitas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/** DTO para crear o actualizar un Rescate */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RescateRequest {
    @NotNull
    private Long reporteId;
    @NotNull
    private Long voluntarioId;
    @NotBlank
    private String observaciones;
}

/** DTO para la respuesta de Rescate */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RescateResponse {
    private Long id;
    private LocalDateTime fecha;
    private String observaciones;
    private RescateEstadoDto estado;
    private Long reporteId;
    private Long voluntarioId;
    private Long perroId; // opcional
}

/** Enum DTO para estado de Rescate */
public enum RescateEstadoDto {
    PENDIENTE,
    EN_PROCESO,
    FINALIZADO
}
