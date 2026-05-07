package com.inmohub.fsbo.ingestor.service.infrastructure.adapters.in.web;

import com.inmohub.fsbo.ingestor.service.application.dtos.FsboResponse;
import com.inmohub.fsbo.ingestor.service.application.usecases.IngestFsboFileUseCase;
import com.inmohub.fsbo.ingestor.service.domain.abstractions.Result;
import com.inmohub.fsbo.ingestor.service.domain.models.OwnerDetails;
import com.inmohub.fsbo.ingestor.service.infrastructure.adapters.out.feign.AuthServiceClient;
import com.inmohub.fsbo.ingestor.service.infrastructure.adapters.out.feign.UserProfileResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fsbo")
@RequiredArgsConstructor
@Tag(
    name = "FSBO Ingestion",
    description = "Endpoints para la gestión masiva de inmuebles FSBO (For Sale By Owner). " +
                  "Permite a propietarios subir archivos CSV con múltiples propiedades para procesamiento asíncrono."
)
@SecurityRequirement(name = "Bearer Authentication")
public class FsboIngestorController {

    private final IngestFsboFileUseCase useCase;
    private final AuthServiceClient authServiceClient;

    @PostMapping(value = "/properties/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @Operation(
        summary = "Subida masiva de inmuebles desde CSV",
        description = "Procesa un archivo CSV con múltiples inmuebles y los vincula al propietario autenticado. " +
                      "El archivo debe seguir el formato definido para FSBO. El procesamiento se realiza de forma asíncrona."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "202",
            description = "Archivo aceptado para procesamiento. Los inmuebles válidos serán procesados.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = FsboResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error de validación en el archivo CSV o formato inválido",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "401",
            description = "No autorizado - Token JWT inválido o ausente",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Prohibido - El usuario no tiene el rol requerido (ADMIN u OWNER)",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor al procesar el archivo",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "503",
            description = "Servicio no disponible - No se pudo verificar el usuario con el servicio de autenticación",
            content = @Content(mediaType = "application/json")
        )
    })
    public ResponseEntity<?> uploadPropertiesBulk(
            @RequestPart("file")
            @Parameter(
                description = "Archivo CSV con los inmuebles a ingestar. Debe seguir el formato FSBO definido.",
                required = true,
                content = @Content(mediaType = "application/octet-stream")
            ) MultipartFile file) {
        try {
            String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
            UUID ownerId = UUID.fromString(userIdStr);

            UserProfileResponse userProfile = authServiceClient.getUserById(ownerId);

            OwnerDetails ownerDetails = new OwnerDetails(
                    ownerId,
                    userProfile.getFullName(),
                    userProfile.email(),
                    userProfile.phone()
            );

            Result<FsboResponse, String> result = useCase.execute(file.getInputStream(), ownerDetails);

            if (!result.isSuccess()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getErrorValue());
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Procesamiento en lote iniciado. Inmuebles válidos aceptados: " + result.getValue());

        } catch (feign.FeignException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("No se pudo verificar la identidad del usuario con el servicio de autenticación.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno procesando el archivo CSV.");
        }
    }
}