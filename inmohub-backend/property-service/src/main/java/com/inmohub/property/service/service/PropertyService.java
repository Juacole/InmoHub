package com.inmohub.property.service.service;

import com.inmohub.property.service.client.AuthClient;
import com.inmohub.property.service.dto.PropertyCreateDTO;
import com.inmohub.property.service.dto.PropertyDTO;
import com.inmohub.property.service.dto.UserResponse;
import com.inmohub.property.service.exception.ResourceNotFoundException;
import com.inmohub.property.service.exception.UserNotActiveException;
import com.inmohub.property.service.mapper.PropertyMapper;
import com.inmohub.property.service.model.Property;
import com.inmohub.property.service.repository.PropertyRepository;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private final PropertyRepository repository;
    private final PropertyMapper mapper;
    private final AuthClient client; // Cliente Feign para comunicación síncrona con Auth-Service


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
     * @param createDTO DTO con la información del inmueble a crear.
     * @return {@link PropertyDTO} con los datos de la propiedad persistida, incluyendo su ID y fechas de auditoría.
     * @throws UserNotActiveException Si el propietario existe pero su cuenta no está activa.
     */
    public PropertyDTO createProperty(PropertyCreateDTO createDTO) {

        try {
            // Si el usuario no existe, feing lanza una excepcion 404
            UserResponse user = client.getUserById(createDTO.ownerId());

            if(!"ACTIVE".equals(user.status())) {
                throw new UserNotActiveException("El propietario no está activo y no puede publicar propiedades.");
            }
        }catch (FeignException.FeignClientException.NotFound e) {
            log.warn("Usuario con ID: {} no existe: {}", createDTO.ownerId(), e);
        }


        Property property = mapper.toEntity(createDTO);
        return mapper.toDTO(repository.save(property));
    }

    /**
     * Recupera el listado completo de propiedades registradas en el sistema.
     *
     * @return Una lista de objetos {@link PropertyDTO} con la información de todos los inmuebles.
     */
    public List<PropertyDTO> getAllProperties() {
            return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Busca una propiedad específica por su identificador único (UUID).
     *
     * @param uuid El identificador único de la propiedad a buscar.
     * @return {@link PropertyDTO} con los detalles del inmueble encontrado.
     * @throws ResourceNotFoundException Si no existe ninguna propiedad con el ID proporcionado en la base de datos.
     */
    public PropertyDTO getPropertyById(UUID uuid) {
        return repository.findById(uuid)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Propiedad no encontrada."));
    }

    /**
     * Filtra y recupera todas las propiedades asociadas a un propietario específico.
     *
     * @param ownerId El UUID del propietario (usuario) del cual se quieren listar los inmuebles.
     * @return Una lista de {@link PropertyDTO} pertenecientes a ese propietario. Puede estar vacía si no tiene inmuebles.
     */
    public List<PropertyDTO> findByOwnerId(UUID ownerId) {
        return repository.findByOwnerId(ownerId).stream()
                .map(mapper::toDTO)
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
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
