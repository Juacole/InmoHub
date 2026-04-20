package com.inmohub.property.service.dtos;

import com.inmohub.property.service.models.Property;

import java.util.UUID;

public record PropertyPhotoDto(
        UUID id,
        Property property,
        String photoUrl,
        Boolean isPrimary
) {
}
