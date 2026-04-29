package com.inmohub.fsbo.ingestor.service.infrastructure.adapters.out.messaging;

import com.inmohub.fsbo.ingestor.service.domain.models.OwnerDetails;
import com.inmohub.fsbo.ingestor.service.domain.ports.ILeadEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KafkaLeadEventPublisher implements ILeadEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC_LEADS = "lead.events";

    @Override
    public void publishOwnerAsLeadEvent(OwnerDetails ownerDetails) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("eventType", "FSBO_OWNER_BULK_UPLOAD");
        payload.put("ownerId", ownerDetails.ownerId());
        payload.put("name", ownerDetails.fullName());
        payload.put("email", ownerDetails.email());
        payload.put("phone", ownerDetails.phone());
        payload.put("source", "FSBO");
        payload.put("message", "El propietario ha realizado una carga masiva de inmuebles.");

        kafkaTemplate.send(TOPIC_LEADS, ownerDetails.toString(), payload);
    }
}