package com.inmohub.property.service.messaging.dtos;

import java.util.UUID;

public record IndividualPropertyCreatedEvent(
        String eventType,
        UUID propertyId,
        UUID ownerId,
        String ownerName,
        String ownerEmail,
        String ownerPhone,
        String ingestionSource
) {
}