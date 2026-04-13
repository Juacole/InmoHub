package com.inmohub.auth.service.dto;

import com.inmohub.auth.service.model.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Objeto de transferencia con la información pública del usuario")
public record UserDTO(
        @Schema(description = "Identificador único del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Nombre de usuario", example = "pepemontana")
        String username,

        @Schema(description = "Correo electrónico", example = "pepe.montana@gmail.com")
        String email,

        @Schema(description = "Nombre", example = "Pepe")
        String firstName,

        @Schema(description = "Apellidos", example = "Montana Botella")
        String lastName,

        @Schema(description = "Teléfono de contacto", example = "600123456")
        String phone,

        @Schema(description = "Rol asignado", example = "AGENT")
        String role,

        @Schema(description = "Estado actual del usuario", example = "ACTIVE")
        UserStatus status,

        @Schema(description = "Fecha de creación del registro")
        LocalDateTime createdAt,

        @Schema(description = "Fecha de última actualización")
        LocalDateTime updatedAt
) {
}
