package com.ecotrack.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecotrack.domain.Resident;

public interface ResidentRepository extends JpaRepository<Resident, UUID> {

    List<Resident> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT cr.resident FROM CollectionRequest cr WHERE cr.collector.id = :collectorId")
    List<Resident> findResidentsByCollectorId(@Param("collectorId") UUID collectorId);

}