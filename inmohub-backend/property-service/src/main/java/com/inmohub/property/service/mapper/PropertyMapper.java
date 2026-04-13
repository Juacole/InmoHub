package com.inmohub.property.service.mapper;

import com.inmohub.property.service.dto.PropertyCreateDTO;
import com.inmohub.property.service.dto.PropertyDTO;
import com.inmohub.property.service.model.Property;
import com.inmohub.property.service.model.enums.PropertyStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = PropertyStatus.class)
public interface PropertyMapper {
    /**
     * Realiza la conversión de entidad "Property" a DTO "PropertyDTO".
     *
     * @param property Entidad JPA.
     * @return objeto DTO PropertyDTO.
     */
    PropertyDTO toDTO(Property property);

    /**
     * Convierte DTO de creación a Entidad.
     * Configura por defecto el estado como AVAILABLE.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", expression = "java(PropertyStatus.AVAILABLE)") // Por defecto se le asigna disponible
    Property toEntity(PropertyCreateDTO createDTO);
}
