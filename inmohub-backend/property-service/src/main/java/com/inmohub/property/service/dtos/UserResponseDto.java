package com.inmohub.property.service.dtos;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        String name,
        String phone,
        String role,
        String status
) {
}
