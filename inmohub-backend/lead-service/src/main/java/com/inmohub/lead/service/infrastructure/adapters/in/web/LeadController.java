package com.inmohub.lead.service.infrastructure.adapters.in.web;

import com.inmohub.lead.service.application.dto.AssignLeadRequest;
import com.inmohub.lead.service.application.dto.CreateLeadRequest;
import com.inmohub.lead.service.application.dto.LeadAssignmentResponse;
import com.inmohub.lead.service.application.dto.LeadResponse;
import com.inmohub.lead.service.application.usecases.AssignLeadUseCase;
import com.inmohub.lead.service.application.usecases.CreateLeadUseCase;
import com.inmohub.lead.service.domain.abstractions.Error;
import com.inmohub.lead.service.domain.abstractions.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/leads")
@RequiredArgsConstructor
@Tag(name = "Leads", description = "Gestión de clientes potenciales (Leads)")
public class LeadController {

    private final CreateLeadUseCase createLeadUseCase;
    private final AssignLeadUseCase assignLeadUseCase;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo Lead", description = "Registra un interesado en una propiedad")
    public Result<LeadResponse, Error> createLead(@RequestBody CreateLeadRequest request) {
        return createLeadUseCase.execute(request);
    }

    @PostMapping("/{leadId}/assign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Asignar un Lead a un Agente", description = "Cambia el estado del Lead a CONTACTED, registra la asignación y genera un log de auditoría.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asignación exitosa"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Lead no encontrado")
    })
    public Result<LeadAssignmentResponse, Error> assignLead(
            @PathVariable UUID leadId,
            @RequestBody AssignLeadRequest request
    ) {
        String currentUserIdStr = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID actionUserId = currentUserIdStr != null ? UUID.fromString(currentUserIdStr) : null;

        return assignLeadUseCase.execute(leadId, request, actionUserId);
    }
}