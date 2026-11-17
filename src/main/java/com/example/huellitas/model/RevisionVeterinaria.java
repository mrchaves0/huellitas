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
public class RevisionVeterinaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime fecha;

    @NotBlank
    private String diagnosticoBasico;

    @NotBlank
    private String observaciones;

    @NotBlank
    private String estadoSalud;

    // Relación con Usuario (veterinario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Usuario veterinario;

    // Relación con Perro
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perro_id", nullable = false)
    private Perro perro;
}
