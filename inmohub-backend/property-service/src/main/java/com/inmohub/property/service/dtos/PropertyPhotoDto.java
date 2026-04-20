package com.inmohub.property.service.dtos;

import java.util.UUID;

public record PropertyPhotoDto(
        UUID id,
        String photoUrl,
        Boolean isPrimary
) {
}
