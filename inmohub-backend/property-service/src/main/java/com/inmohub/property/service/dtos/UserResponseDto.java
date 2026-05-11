package com.inmohub.property.service.dtos;

import java.util.Set;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        String name,
        String phone,
        Set<String> roles,
        String status
) {
}
