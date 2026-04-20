package com.inmohub.property.service.services;

import com.inmohub.property.service.clients.AuthClient;
import com.inmohub.property.service.dtos.PropertyCreateDto;
import com.inmohub.property.service.dtos.PropertyDto;
import com.inmohub.property.service.dtos.UserResponseDto;
import com.inmohub.property.service.exceptions.ResourceNotFoundException;
import com.inmohub.property.service.exceptions.UserNotActiveException;
import com.inmohub.property.service.mappers.IPropertyMapper;
import com.inmohub.property.service.models.Property;
import com.inmohub.property.service.models.PropertyFeature;
import com.inmohub.property.service.models.PropertyPhoto;
import com.inmohub.property.service.models.enums.PropertyStatus;
import com.inmohub.property.service.repositories.IPropertyRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * Servicio de lógica de negocio para la gestión de propiedades inmobiliarias.
 *
 * Esta clase actúa como intermediario entre el controlador y la capa de persistencia.
 * Su responsabilidad principal es orquestar las operaciones CRUD sobre los inmuebles,
 * incluyendo la validación cruzada con el microservicio de autenticación (Auth-Service)
 * para asegurar la integridad de los datos del propietario.
 */
@Service
@AllArgsConstructor
@Slf4j
public class PropertyService {
    private final IPropertyRepository propertyRepository;
    private final IPropertyMapper propertyMapper;
    private final AuthClient client; // Cliente Feign para comunicación síncrona con Auth-Service
    private final FirebaseStorageService firebaseService;

    /**
     * Crea y persiste una nueva propiedad en la base de datos tras validar al propietario.
     *
     * Flujo de validación distribuida:
     * <ol>
     * <li>El servicio recibe la petición de creación con el ID del propietario ("ownerId").</li>
     * <li>Realiza una llamada HTTP síncrona, vía Feign Client, al microservicio {@code auth-service}.</li>
     * <li>Verifica si el usuario existe y si su estado es {@code ACTIVE}.</li>
     * <li>Si el usuario no está activo, se bloquea la operación lanzando {@link UserNotActiveException}.</li>
     * <li>Si el usuario no existe (404), se captura la excepción y se loguea una advertencia, permitiendo la creación (según reglas de negocio actuales).</li>
     * </ol>
     *
     * @param dto DTO con la información del inmueble a crear.
     * @return {@link PropertyDto} con los datos de la propiedad persistida, incluyendo su ID y fechas de auditoría.
     * @throws UserNotActiveException Si el propietario existe pero su cuenta no está activa.
     */
    @Transactional
    public PropertyDto createProperty(PropertyCreateDto dto, List<MultipartFile> photos, UUID ownerId) throws IOException {
        validateOwnerStatus(ownerId);

        Property property = propertyMapper.toEntity(dto);
        property.setOwnerId(ownerId);
        property.setStatus(PropertyStatus.AVAILABLE);

        if (dto.features() != null) {
            dto.features().forEach(f -> {
                PropertyFeature feature = new PropertyFeature();
                feature.setFeatureName(f.featureName());
                feature.setFeatureValue(f.featureValue());
                property.addFeature(feature);
            });
        }

        if (photos != null && !photos.isEmpty()) {
            for (int i = 0; i < photos.size(); i++) {
                String url = firebaseService.uploadPhoto(photos.get(i));
                PropertyPhoto photo = new PropertyPhoto();
                photo.setPhotoUrl(url);
                photo.setIsPrimary(i == 0); // La primera foto se marca como principal por defecto
                property.addPhoto(photo);
            }
        }

        PropertyDto savedProperty = propertyMapper.toDTO(propertyRepository.save(property));
        log.info("Propiedad creada con éxito: ID {}", savedProperty.id());

        return savedProperty;
    }

    private void validateOwnerStatus(UUID ownerId) {
        try {
            UserResponseDto user = client.getUserById(ownerId);
            if (!"ACTIVE".equalsIgnoreCase(user.status())) {
                throw new UserNotActiveException("El usuario debe estar activo para publicar.");
            }
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("El propietario no existe en Auth-Service.");
        }
    }

    /**
     * Recupera el listado completo de propiedades registradas en el sistema.
     *
     * @return Una lista de objetos {@link PropertyDto} con la información de todos los inmuebles.
     */
    public List<PropertyDto> getAllProperties() {
            return propertyRepository.findAll().stream()
                .map(propertyMapper::toDTO)
                .toList();
    }

    /**
     * Busca una propiedad específica por su identificador único (UUID).
     *
     * @param uuid El identificador único de la propiedad a buscar.
     * @return {@link PropertyDto} con los detalles del inmueble encontrado.
     * @throws ResourceNotFoundException Si no existe ninguna propiedad con el ID proporcionado en la base de datos.
     */
    public PropertyDto getPropertyById(UUID uuid) {
        return propertyRepository.findById(uuid)
                .map(propertyMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Propiedad no encontrada."));
    }

    /**
     * Filtra y recupera todas las propiedades asociadas a un propietario específico.
     *
     * @param ownerId El UUID del propietario (usuario) del cual se quieren listar los inmuebles.
     * @return Una lista de {@link PropertyDto} pertenecientes a ese propietario. Puede estar vacía si no tiene inmuebles.
     */
    public List<PropertyDto> findByOwnerId(UUID ownerId) {
        return propertyRepository.findByOwnerId(ownerId).stream()
                .map(propertyMapper::toDTO)
                .toList();
    }

    /**
     * Elimina una propiedad de la base de datos.
     *
     * @param id El identificador único de la propiedad a eliminar.
     * @return {@code true} si la propiedad existía y fue eliminada correctamente,
     * {@code false} si la propiedad no existía y no se realizó ninguna acción.
     */
    public boolean deleteById(UUID id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
