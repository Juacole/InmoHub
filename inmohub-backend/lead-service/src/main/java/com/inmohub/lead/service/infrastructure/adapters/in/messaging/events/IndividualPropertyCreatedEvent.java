package com.inmohub.lead.service.infrastructure.adapters.in.messaging.events;

import java.util.UUID;

public record IndividualPropertyCreatedEvent(
        String eventType,
        UUID propertyId,
        UUID ownerId,
        String ownerName,
        String ownerEmail,
        String ownerPhone,
        String ingestionSource
) {}