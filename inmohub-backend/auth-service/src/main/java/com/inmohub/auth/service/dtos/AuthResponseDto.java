package com.inmohub.auth.service.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthResponseDto(
        @NotBlank(message = "El token es obligatorio")
        String accessToken,
        @NotBlank(message = "El refresh token es obligatorio")
        String refreshToken
) {}