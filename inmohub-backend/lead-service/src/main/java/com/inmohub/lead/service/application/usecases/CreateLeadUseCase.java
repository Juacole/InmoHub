package com.inmohub.lead.service.application.usecases;

import com.inmohub.lead.service.application.dto.CreateLeadRequest;
import com.inmohub.lead.service.application.dto.LeadResponse;
import com.inmohub.lead.service.domain.abstractions.Error;
import com.inmohub.lead.service.domain.abstractions.Result;
import com.inmohub.lead.service.domain.model.Lead;
import com.inmohub.lead.service.domain.ports.ILeadRepository;
import com.inmohub.lead.service.domain.valueobjetcs.Email;

public class CreateLeadUseCase {
    private final ILeadRepository leadRepository;

    public CreateLeadUseCase(ILeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public Result<LeadResponse, Error> execute(CreateLeadRequest request) {
        Lead newLead = Lead.create(
                request.name(),
                new Email(request.email()),
                request.phone(),
                request.message(),
                request.source(),
                request.propertyId()
        );

        Lead savedLead = leadRepository.saveLead(newLead);

        return Result.success(
                new LeadResponse(
                        savedLead.getId(),
                        savedLead.getName(),
                        savedLead.getEmail().value(),
                        savedLead.getPhone(),
                        savedLead.getStatus(),
                        savedLead.getPropertyId()
                )
        );
    }
}