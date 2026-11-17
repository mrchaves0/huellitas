package com.example.huellitas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO para registrar un nuevo usuario */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String nombre;

    @Email
    @NotBlank
    private String correo;

    @NotBlank
    private String contrasena;

    @NotBlank
    private String telefono;

    @NotNull
    private UsuarioRolDto rol;
}

/** DTO para login */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Email
    @NotBlank
    private String correo;

    @NotBlank
    private String contrasena;
}

/** DTO respuesta de autenticación (token simplificado) */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token; // En un caso real sería JWT
    private Long usuarioId;
    private String nombre;
    private String correo;
    private UsuarioRolDto rol;
}

/** Enum DTO para el rol (para evitar exponer entidad directamente) */
public enum UsuarioRolDto {
    CIUDADANO,
    VOLUNTARIO,
    VETERINARIO,
    ADMINISTRADOR
}
