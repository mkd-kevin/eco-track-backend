
package com.ecotrack.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecotrack.domain.CollectionRequest;
import com.ecotrack.domain.CollectionStatus;
import com.ecotrack.domain.WasteRecord;
import com.ecotrack.repository.CollectionRequestRepository;
import com.ecotrack.repository.WasteRecordRepository;

@Service
public class WasteRecordService {

    private final WasteRecordRepository wasteRecordRepository;
    private final CollectionRequestRepository collectionRequestRepository;

    public WasteRecordService(
            WasteRecordRepository wasteRecordRepository,
            CollectionRequestRepository collectionRequestRepository) {
        this.wasteRecordRepository = wasteRecordRepository;
        this.collectionRequestRepository = collectionRequestRepository;
    }

    public String saveWasteRecord(
            UUID collectionRequestId, WasteRecord wasteRecord) {

        Optional<CollectionRequest> existingRequest =
                collectionRequestRepository.findById(collectionRequestId);

        if (existingRequest.isEmpty()) {
            return "The collection request with that id does not exist";
        }

        CollectionRequest collectionRequest = existingRequest.get();

        if (collectionRequest.getStatus() != CollectionStatus.COLLECTED) {
            return "Waste records are only for COLLECTED collection requests";
        }

        wasteRecord.setRecordedDate(LocalDate.now());
        wasteRecord.setCollectionRequest(collectionRequest);

        wasteRecordRepository.save(wasteRecord);

        return "Waste record saved successfully";
    }

    public List<WasteRecord> getWasteRecordsByResident(String houseNumber) {
        return wasteRecordRepository
                .findWasteRecordsByResidentHouseNumber(houseNumber);
    }

    public Optional<WasteRecord> getWasteRecordById(UUID id) {
        return wasteRecordRepository.findById(id);
    }

    public String deleteWasteRecord(UUID id) {

        Optional<WasteRecord> existingWasteRecord =
                wasteRecordRepository.findById(id);

        if (existingWasteRecord.isPresent()) {
            wasteRecordRepository.delete(existingWasteRecord.get());

            return "Waste record deleted successfully";
        }

        return "The waste record with that id does not exist";
    }
}
