package com.ecotrack.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecotrack.domain.WasteType;

public interface WasteTypeRepository extends JpaRepository<WasteType, UUID> {
}