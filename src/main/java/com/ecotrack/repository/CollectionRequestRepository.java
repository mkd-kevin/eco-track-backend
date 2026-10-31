
package com.ecotrack.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecotrack.domain.CollectionRequest;
import com.ecotrack.domain.CollectionStatus;

public interface CollectionRequestRepository extends JpaRepository<CollectionRequest, UUID> {

    List<CollectionRequest> findByStatusOrderByRequestDateAsc(CollectionStatus status);

    List<CollectionRequest> findByRequestDateBetweenOrderByRequestDateAsc(
            LocalDate start, LocalDate end);

    boolean existsByCollectorIdAndRequestDateAndStatusNot(
            UUID collectorId,
            LocalDate requestDate,
            CollectionStatus status
    );

    @Query("""
            SELECT c.status, COUNT(c)
            FROM CollectionRequest c
            GROUP BY c.status
            """)
    List<Object[]> countCollectionRequestsByStatus();

    @Modifying
    @Query("""
            UPDATE CollectionRequest c
            SET c.status = com.ecotrack.domain.CollectionStatus.CANCELLED
            WHERE c.collector.id = :collectorId
            AND c.requestDate = :date
            AND c.status <> com.ecotrack.domain.CollectionStatus.COLLECTED
            """)
    int cancelCollectionRequestsByCollectorAndDate(
            @Param("collectorId") UUID collectorId,
            @Param("date") LocalDate date
    );

    long countByRequestDate(LocalDate requestDate);

    long countByStatus(CollectionStatus status);
}
