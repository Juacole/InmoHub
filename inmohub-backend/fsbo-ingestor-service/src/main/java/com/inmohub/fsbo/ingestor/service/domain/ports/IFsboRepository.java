package com.inmohub.fsbo.ingestor.service.domain.ports;

import com.inmohub.fsbo.ingestor.service.domain.models.FsboBatch;

public interface IFsboRepository {
    void saveBatch(FsboBatch batch);
    boolean existsByAddressAndCity(String address, String city);
}
