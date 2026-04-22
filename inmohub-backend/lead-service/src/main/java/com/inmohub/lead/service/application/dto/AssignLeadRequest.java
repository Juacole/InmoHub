package com.inmohub.lead.service.application.dto;

import java.util.UUID;

public record AssignLeadRequest(
        UUID agentId,
        String assignmentNotes
) {}