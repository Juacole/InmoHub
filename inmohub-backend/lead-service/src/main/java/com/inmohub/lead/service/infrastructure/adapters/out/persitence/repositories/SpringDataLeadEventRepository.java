package com.inmohub.lead.service.infrastructure.adapters.out.persitence.repositories;

import com.inmohub.lead.service.infrastructure.adapters.out.persitence.entities.LeadEventJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataLeadEventRepository extends JpaRepository<LeadEventJpaEntity, UUID> {
}
