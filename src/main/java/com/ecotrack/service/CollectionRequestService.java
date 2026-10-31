
package com.ecotrack.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecotrack.domain.CollectionRequest;
import com.ecotrack.domain.CollectionStatus;
import com.ecotrack.repository.CollectionRequestRepository;
import com.ecotrack.repository.WasteRecordRepository;

@Service
public class CollectionRequestService {

    private final CollectionRequestRepository collectionRequestRepository;
    private final WasteRecordRepository wasteRecordRepository;

    public CollectionRequestService(
            CollectionRequestRepository collectionRequestRepository,
            WasteRecordRepository wasteRecordRepository) {
        this.collectionRequestRepository = collectionRequestRepository;
        this.wasteRecordRepository = wasteRecordRepository;
    }

    @Transactional
    public String saveCollectionRequest(CollectionRequest collectionRequest) {

        if (collectionRequest.getRequestDate().isBefore(LocalDate.now())) {
            return "Collection request date cannot be in the past";
        }

        boolean existingRequest =
                collectionRequestRepository.existsByCollectorIdAndRequestDateAndStatusNot(
                        collectionRequest.getCollector().getId(),
                        collectionRequest.getRequestDate(),
                        CollectionStatus.CANCELLED
                );

        if (existingRequest) {
            return "Collector already has a collection on that date";
        }

        collectionRequest.setStatus(CollectionStatus.SUBMITTED);

        collectionRequestRepository.save(collectionRequest);

        return "Collection request saved successfully";
    }

    @Transactional
    public String updateCollectionRequestStatus(
            UUID id, CollectionStatus newStatus) {

        Optional<CollectionRequest> existingRequest =
                collectionRequestRepository.findById(id);

        if (existingRequest.isEmpty()) {
            return "The collection request with that id does not exist";
        }

        CollectionRequest collectionRequest = existingRequest.get();

        CollectionStatus currentStatus = collectionRequest.getStatus();

        boolean validTransition =
                (currentStatus == CollectionStatus.SUBMITTED
                        && (newStatus == CollectionStatus.ASSIGNED
                        || newStatus == CollectionStatus.CANCELLED))
                ||
                (currentStatus == CollectionStatus.ASSIGNED
                        && (newStatus == CollectionStatus.COLLECTED
                        || newStatus == CollectionStatus.CANCELLED));

        if (!validTransition) {
            return "Cannot change status from " + currentStatus
                    + " to " + newStatus;
        }

        collectionRequest.setStatus(newStatus);
        collectionRequestRepository.save(collectionRequest);

        return "Collection request status updated successfully";
    }

    public List<CollectionRequest> getAllCollectionRequests() {
        return collectionRequestRepository.findAll();
    }

    public Optional<CollectionRequest> getCollectionRequestById(UUID id) {
        return collectionRequestRepository.findById(id);
    }

    public String updateCollectionRequest(
            UUID id, CollectionRequest updatedCollectionRequest) {

        Optional<CollectionRequest> existingCollectionRequest =
                collectionRequestRepository.findById(id);

        if (existingCollectionRequest.isPresent()) {
            CollectionRequest collectionRequest =
                    existingCollectionRequest.get();

            collectionRequest.setRequestDate(updatedCollectionRequest.getRequestDate());
            collectionRequest.setLocation(updatedCollectionRequest.getLocation());
            collectionRequest.setResident(updatedCollectionRequest.getResident());
            collectionRequest.setCollector(updatedCollectionRequest.getCollector());

            collectionRequestRepository.save(collectionRequest);

            return "Collection request updated successfully";
        }

        return "The collection request with that id does not exist";
    }

    public String deleteCollectionRequest(UUID id) {
        Optional<CollectionRequest> existingCollectionRequest =
                collectionRequestRepository.findById(id);

        if (existingCollectionRequest.isPresent()) {
            CollectionRequest collectionRequest =
                    existingCollectionRequest.get();

            boolean hasWasteRecords =
                    wasteRecordRepository.existsByCollectionRequest(collectionRequest);

            if (hasWasteRecords) {
                return "That collection request has waste records, delete them first";
            }

            collectionRequestRepository.delete(collectionRequest);

            return "Collection request deleted successfully";
        }

        return "The collection request with that id does not exist";
    }

    public List<CollectionRequest> findRequestsByStatus(CollectionStatus status) {
        return collectionRequestRepository.findByStatusOrderByRequestDateAsc(status);
    }

    public List<CollectionRequest> findRequestsBetweenDates(
            LocalDate start, LocalDate end) {
        return collectionRequestRepository.findByRequestDateBetweenOrderByRequestDateAsc(
                start, end);
    }

    public List<Object[]> getCollectionRequestStatsByStatus() {
        return collectionRequestRepository.countCollectionRequestsByStatus();
    }

    @Transactional
    public String cancelCollectionRequestsByCollectorAndDate(
            UUID collectorId, LocalDate date) {

        int cancelledCount =
                collectionRequestRepository
                        .cancelCollectionRequestsByCollectorAndDate(
                                collectorId, date);

        return cancelledCount + " collection requests cancelled";
    }
}
