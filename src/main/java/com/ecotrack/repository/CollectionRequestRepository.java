
package com.ecotrack.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecotrack.domain.CollectionRequest;
import com.ecotrack.domain.CollectionStatus;

public interface CollectionRequestRepository extends JpaRepository<CollectionRequest, UUID> {

    List<CollectionRequest> findByStatusOrderByRequestDateAsc(CollectionStatus status);

    List<CollectionRequest> findByRequestDateBetweenOrderByRequestDateAsc(LocalDate start, LocalDate end);

}
