package com.inmohub.lead.service.infrastructure.adapters.out.persitence.mappers;

import com.inmohub.lead.service.domain.model.Lead;
import com.inmohub.lead.service.domain.model.enums.LeadSource;
import com.inmohub.lead.service.domain.model.enums.LeadStatus;
import com.inmohub.lead.service.domain.valueobjetcs.Email;
import com.inmohub.lead.service.infrastructure.adapters.out.persitence.entities.LeadJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface LeadMapper {
    LeadJpaEntity toJpaEntity(Lead domainLead);
    Lead toDomainEntity(LeadJpaEntity jpaEntity);

    @ObjectFactory
    default Lead createLeadFromEntity(LeadJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return Lead.create(
                entity.getName(),
                new Email(entity.getEmail()),
                entity.getPhone(),
                entity.getMessage(),
                LeadSource.valueOf(entity.getSource()),
                entity.getPropertyId()
        );
    }
}