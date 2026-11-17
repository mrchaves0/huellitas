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
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    @Column(unique = true)
    private String correo;

    @NotBlank
    private String contrasenaHash;

    @NotBlank
    private String telefono;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UsuarioRol rol;

    // Relaciones bidireccionales opcionales (no cascade)
    @OneToMany(mappedBy = "reportante")
    private Set<Reporte> reportes;

    @OneToMany(mappedBy = "voluntario")
    private Set<Rescate> rescates;

    @OneToMany(mappedBy = "veterinario")
    private Set<RevisionVeterinaria> revisionesVeterinarias;

    @OneToMany(mappedBy = "adoptante")
    private Set<SolicitudAdopcion> solicitudesAdopcion;
}
