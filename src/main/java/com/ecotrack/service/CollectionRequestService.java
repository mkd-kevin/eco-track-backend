
package com.ecotrack.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecotrack.domain.CollectionRequest;
import com.ecotrack.repository.CollectionRequestRepository;

@Service
public class CollectionRequestService {

    private final CollectionRequestRepository collectionRequestRepository;

    public CollectionRequestService(
            CollectionRequestRepository collectionRequestRepository) {
        this.collectionRequestRepository = collectionRequestRepository;
    }

    public String saveCollectionRequest(CollectionRequest collectionRequest) {
        collectionRequestRepository.save(collectionRequest);
        return "Collection request saved successfully";
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
            collectionRequestRepository.delete(existingCollectionRequest.get());
            return "Collection request deleted successfully";
        }

        return "The collection request with that id does not exist";
    }
}
