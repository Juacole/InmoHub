package com.inmohub.fsbo.ingestor.service.domain.ports;

import com.inmohub.fsbo.ingestor.service.domain.models.FsboBatch;

public interface IPropertyEventPublisher {
    void publishBulkProperties(FsboBatch fsboBatch);
}
