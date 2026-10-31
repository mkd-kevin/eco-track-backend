package com.ecotrack.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecotrack.domain.WasteType;

public interface WasteTypeRepository extends JpaRepository<WasteType, UUID> {

    @Query("SELECT w FROM WasteType w WHERE w.collectors IS EMPTY")
    List<WasteType> findUnusedWasteTypes();

}