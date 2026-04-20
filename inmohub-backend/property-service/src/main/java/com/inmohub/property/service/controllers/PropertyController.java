package com.inmohub.property.service.controllers;

import com.inmohub.property.service.dtos.PropertyCreateDto;
import com.inmohub.property.service.dtos.PropertyDto;
import com.inmohub.property.service.services.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para la gestión de inmuebles.
 * Expone los endpoints para crear, consultar y eliminar propiedades.
 */
@RestController
@RequestMapping("/api/v1/properties")
@AllArgsConstructor
@Tag(name = "Gestión de Propiedades", description = "Endpoints para el ciclo de vida de los inmuebles (CRUD)")
public class PropertyController {

    private final PropertyService propertyService;

    @Operation(
            summary = "Publicar una nueva propiedad con imágenes",
            description = "Crea un inmueble vinculando imágenes subidas a Firebase." +
                    "El owner_id se extrae automáticamente del token de seguridad (X-User-Id)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PropertyDto.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos de entrada", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado para realizar esta operación", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto: El propietario no existe o no está activo", content = @Content)
    })
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PropertyDto> create(
            @Parameter(description = "Metadatos de la propiedad en formato JSON", required = true)
            @RequestPart("property") @Valid PropertyCreateDto propertyCreateDto,

            @Parameter(description = "Listado de archivos de imagen (jpg, png).")
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos
    ) throws IOException {

        // Recuperación del ID del usuario desde SecurityContext (inyectado por HeaderAuthenticationFilter)
        String userIdHeader = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID ownerId = UUID.fromString(userIdHeader);

        PropertyDto response = propertyService.createProperty(propertyCreateDto, photos, ownerId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todas las propiedades", description = "Recupera el listado completo de inmuebles disponibles en el sistema.")
    @ApiResponse(responseCode = "200", description = "Listado recuperado correctamente")
    @GetMapping("/all")
    public ResponseEntity<List<PropertyDto>> getAll() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @Operation(summary = "Buscar propiedad por ID", description = "Obtiene los detalles de un inmueble específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PropertyDto.class))),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrada", content = @Content)
    })
    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<PropertyDto> getById(@PathVariable(name = "id") UUID id) {
        PropertyDto p = propertyService.getPropertyById(id);

        if(p != null) return ResponseEntity.ok(p);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @Operation(summary = "Listar propiedades de un propietario", description = "Devuelve todos los inmuebles asociados a un usuario específico.")
    @ApiResponse(responseCode = "200", description = "Listado recuperado (puede estar vacío)")
    @GetMapping("/search-by-owner-id/{id}")
    public ResponseEntity<List<PropertyDto>> getByOwnerId(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(propertyService.findByOwnerId(id));
    }

    @Operation(summary = "Eliminar propiedad", description = "Elimina físicamente un inmueble de la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la propiedad a eliminar")
    })
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        boolean deleted = propertyService.deleteById(id);

        if (deleted) {
            return ResponseEntity
                    .ok()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
