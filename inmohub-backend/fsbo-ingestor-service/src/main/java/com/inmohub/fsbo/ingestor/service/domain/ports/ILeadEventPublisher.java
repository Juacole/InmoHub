package com.inmohub.fsbo.ingestor.service.domain.ports;

import com.inmohub.fsbo.ingestor.service.domain.models.OwnerDetails;

public interface ILeadEventPublisher {
    void publishOwnerAsLeadEvent(OwnerDetails ownerDetails);
}
