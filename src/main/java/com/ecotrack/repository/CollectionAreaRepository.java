package com.ecotrack.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecotrack.domain.CollectionArea;

public interface CollectionAreaRepository extends JpaRepository<CollectionArea, UUID> {
}