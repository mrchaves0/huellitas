package com.example.huellitas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rescate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime fecha;

    @NotBlank
    private String observaciones;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RescateEstado estado;

    // Relación con Reporte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporte_id", nullable = false)
    private Reporte reporte;

    // Relación con Usuario (voluntario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voluntario_id", nullable = false)
    private Usuario voluntario;

    // Relación opcional con Perro (one-to-one o many-to-one)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perro_id")
    private Perro perro;
}
