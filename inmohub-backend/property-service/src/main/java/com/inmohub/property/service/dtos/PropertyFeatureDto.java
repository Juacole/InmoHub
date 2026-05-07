package com.inmohub.property.service.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO que representa una característica opcional de la propiedad")
public record PropertyFeatureDto(
        @Schema(description = "Nombre de la característica", example = "Habitaciones")
        @NotBlank
        String featureName,

        @Schema(description = "Valor de la característica", example = "4")
        @NotBlank
        String featureValue
) {
}
