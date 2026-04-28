package com.inmohub.fsbo.ingestor.service.infrastructure.adapters.out.messaging;

import com.inmohub.fsbo.ingestor.service.domain.models.PropertyRecord;
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
    private static final String TOPIC = "lead.events";

    @Override
    public void publishLeadCreatedEvent(PropertyRecord property) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", property.getOwnerName());
        payload.put("email", property.getOwnerEmail());
        payload.put("phone", property.getOwnerPhone());
        payload.put("source", "FSBO");
        payload.put("message", property.getPropertyTitle());

        kafkaTemplate.send(TOPIC, property.getId().toString(), payload);
    }
}