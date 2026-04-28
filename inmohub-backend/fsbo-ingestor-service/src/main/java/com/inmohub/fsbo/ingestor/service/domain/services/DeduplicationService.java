package com.inmohub.fsbo.ingestor.service.domain.services;

import com.inmohub.fsbo.ingestor.service.domain.models.PropertyRecord;
import com.inmohub.fsbo.ingestor.service.domain.ports.IFsboRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeduplicationService {

    private final IFsboRepository repository;

    public DeduplicationService(IFsboRepository repository) {
        this.repository = repository;
    }

    public void processPotentiallyDuplicated(List<PropertyRecord> properties) {
        if (properties == null || properties.isEmpty()) return;

        Set<String> propertyKeysInCurrentBatch = new HashSet<>();

        for (PropertyRecord property : properties) {
            if (!property.canBeProcessed()) continue;

            String propertyKey = generatePropertyKey(property);

            if (isDuplicatedInCurrentBatch(propertyKey, propertyKeysInCurrentBatch)) {
                property.markAsError("Este inmueble ya aparece en el archivo de carga.");
                continue;
            }

            if (existsInSystem(property)) {
                property.markAsError("Este inmueble ya se encuentra registrado en el sistema.");
                continue;
            }

            propertyKeysInCurrentBatch.add(propertyKey);
        }
    }

    private String generatePropertyKey(PropertyRecord property) {
        return (property.getAddress() + "|" + property.getCity())
                .toLowerCase()
                .replaceAll("\\s+", "");
    }

    private boolean isDuplicatedInCurrentBatch(String key, Set<String> seenKeys) {
        return !seenKeys.add(key);
    }

    private boolean existsInSystem(PropertyRecord record) {
        return repository.existsByAddressAndCity(
                record.getAddress(),
                record.getCity()
        );
    }
}
