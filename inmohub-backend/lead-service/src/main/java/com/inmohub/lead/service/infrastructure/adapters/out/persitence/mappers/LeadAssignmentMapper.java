package com.inmohub.lead.service.infrastructure.adapters.out.persitence.mappers;

import com.inmohub.lead.service.domain.model.LeadAssignment;
import com.inmohub.lead.service.infrastructure.adapters.out.persitence.entities.LeadAssignmentJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeadAssignmentMapper {
    LeadAssignmentJpaEntity toJpaEntity(LeadAssignment leadAssignment);
}
