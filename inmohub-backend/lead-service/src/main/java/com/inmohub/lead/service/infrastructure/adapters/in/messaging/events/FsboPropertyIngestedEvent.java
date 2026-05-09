package com.inmohub.lead.service.infrastructure.adapters.in.messaging.events;

import java.util.UUID;

public record FsboPropertyIngestedEvent(
        String eventType,
        UUID ownerId,
        String ownerName,
        String ownerEmail,
        String ownerPhone,
        String ingestionSource,
        UUID propertyId
) {}