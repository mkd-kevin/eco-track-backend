
package com.ecotrack.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecotrack.domain.CollectionRequest;
import com.ecotrack.domain.WasteRecord;

public interface WasteRecordRepository
        extends JpaRepository<WasteRecord, UUID> {

    @Query("""
            SELECT w
            FROM WasteRecord w
            WHERE w.collectionRequest.resident.houseNumber = :houseNumber
            ORDER BY w.recordedDate DESC
            """)
    List<WasteRecord> findWasteRecordsByResidentHouseNumber(
            @Param("houseNumber") String houseNumber
    );

    boolean existsByCollectionRequest(CollectionRequest collectionRequest);
}
