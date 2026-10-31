package com.ecotrack.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecotrack.domain.Resident;

public interface ResidentRepository extends JpaRepository<Resident, UUID> {
}