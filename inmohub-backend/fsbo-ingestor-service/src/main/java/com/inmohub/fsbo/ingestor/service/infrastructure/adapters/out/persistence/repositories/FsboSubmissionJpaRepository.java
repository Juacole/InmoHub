package com.inmohub.fsbo.ingestor.service.infrastructure.adapters.out.persistence.repositories;

import com.inmohub.fsbo.ingestor.service.infrastructure.adapters.out.persistence.entities.FsboSubmissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FsboSubmissionJpaRepository extends JpaRepository<FsboSubmissionJpaEntity, UUID> {
    boolean existsByAddressIgnoreCaseAndCityIgnoreCaseAndStatus(String address, String city, String status);
}
