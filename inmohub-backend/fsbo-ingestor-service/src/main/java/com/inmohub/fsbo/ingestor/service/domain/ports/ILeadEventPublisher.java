package com.inmohub.fsbo.ingestor.service.domain.ports;

import com.inmohub.fsbo.ingestor.service.domain.models.PropertyRecord;

public interface ILeadEventPublisher {
    void publishLeadCreatedEvent(PropertyRecord property);
}
