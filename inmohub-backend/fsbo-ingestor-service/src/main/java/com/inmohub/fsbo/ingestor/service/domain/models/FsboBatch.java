package com.inmohub.fsbo.ingestor.service.domain.models;

import com.inmohub.fsbo.ingestor.service.domain.abstractions.DomainException;

import java.time.LocalDateTime;
import java.util.*;

public class FsboBatch {
    private final UUID ownerId;
    private final LocalDateTime uploadedAt;
    private final List<PropertyRecord> properties;

    private FsboBatch(UUID id, LocalDateTime uploadedAt, List<PropertyRecord> properties) {
        this.ownerId = Objects.requireNonNull(id, "El ID del batch es obligatorio.");
        this.uploadedAt = Objects.requireNonNull(uploadedAt, "La fecha de carga es obligatoria.");

        if (properties == null || properties.isEmpty()) {
            throw new DomainException("Un lote debe contener al menos un registro.");
        }
        this.properties = new ArrayList<>(properties);
    }

    public static FsboBatch create(UUID batchId, LocalDateTime uploadedAt, List<PropertyRecord> records) {
        return new FsboBatch(batchId, uploadedAt, records);
    }

    public List<PropertyRecord> getValidProperties() {
        return properties.stream().filter(PropertyRecord::isValid).toList();
    }

    public List<PropertyRecord> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public int totalRecords() {
        return properties.size();
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}