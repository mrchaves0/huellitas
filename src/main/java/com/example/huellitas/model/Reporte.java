package com.example.huellitas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @NotNull
    private LocalDateTime fecha;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReporteEstado estado;

    // Relación con Usuario (reportante)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario reportante;

    @OneToMany(mappedBy = "reporte")
    private Set<Rescate> rescates;
}
