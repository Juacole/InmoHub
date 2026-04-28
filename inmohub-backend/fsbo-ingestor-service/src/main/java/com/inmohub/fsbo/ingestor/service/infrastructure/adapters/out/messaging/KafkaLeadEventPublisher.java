package com.inmohub.fsbo.ingestor.service.infrastructure.adapters.out.messaging;

import com.inmohub.fsbo.ingestor.service.domain.models.FsboRecord;
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
    public void publishLeadCreatedEvent(FsboRecord record) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", record.getOwnerName());
        payload.put("email", record.getOwnerEmail());
        payload.put("phone", record.getOwnerPhone());
        payload.put("source", "FSBO");
        payload.put("message", record.getPropertyTitle());

        kafkaTemplate.send(TOPIC, record.getId().toString(), payload);
    }
}