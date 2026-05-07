package com.inmohub.property.service.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "DTO que representa una imagen asociada a una propiedad")
public record PropertyPhotoDto(
        @Schema(description = "Identificador único de la foto", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "URL pública de la imagen en Firebase Storage", example = "https://firebasestorage.googleapis.com/...")
        String photoUrl,

        @Schema(description = "Indica si esta imagen es la principal del inmueble", example = "true")
        Boolean isPrimary
) {
}
