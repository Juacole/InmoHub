package com.inmohub.fsbo.ingestor.service.infrastructure.adapters.out.feign;

import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
    public String getFullName() {
        return firstName + " " + lastName;
    }
}