package com.ecotrack.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecotrack.domain.CollectionRequest;

public interface CollectionRequestRepository extends JpaRepository<CollectionRequest, UUID> {
}