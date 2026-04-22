package com.inmohub.lead.service.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record LeadAssignmentResponse(
        UUID leadId,
        UUID agentId,
        LocalDateTime assignedtAt
) {
}
