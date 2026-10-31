package com.ecotrack.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecotrack.domain.Collector;

public interface CollectorRepository extends JpaRepository<Collector, UUID> {
}