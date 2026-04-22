package com.inmohub.lead.service.application.usecases.errors;

import com.inmohub.lead.service.domain.abstractions.Error;

public record LeadNotFound(String message, Exception exception) implements Error {
    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Exception getExcepcion() {
        return this.exception;
    }
}
