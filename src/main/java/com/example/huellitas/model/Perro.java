package com.example.huellitas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotNull
    private Integer edadAproximada;

    @NotBlank
    private String sexo; // could be enum

    @NotBlank
    private String tamano; // could be enum

    @NotBlank
    private String descripcion;

    @NotBlank
    private String fotografia; // URL

    @NotNull
    @Enumerated(EnumType.STRING)
    private PerroEstado estado;

    // Relaciones opcionales
    @OneToMany(mappedBy = "perro")
    private Set<Rescate> rescates;

    @OneToMany(mappedBy = "perro")
    private Set<RevisionVeterinaria> revisionesVeterinarias;

    @OneToMany(mappedBy = "perro")
    private Set<SolicitudAdopcion> solicitudesAdopcion;
}
