package com.ecotrack.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecotrack.domain.CollectionArea;

public interface CollectionAreaRepository extends JpaRepository<CollectionArea, UUID> {

    @Query("SELECT ca FROM CollectionArea ca WHERE NOT EXISTS (SELECT c FROM Collector c WHERE c.collectionArea = ca) ORDER BY ca.areaNumber ASC")
    List<CollectionArea> findFreeCollectionAreas();

}