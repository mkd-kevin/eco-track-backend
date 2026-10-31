package com.ecotrack.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecotrack.domain.CollectionArea;
import com.ecotrack.repository.CollectionAreaRepository;

@Service
public class CollectionAreaService {

    private final CollectionAreaRepository collectionAreaRepository;

    public CollectionAreaService(CollectionAreaRepository collectionAreaRepository) {
        this.collectionAreaRepository = collectionAreaRepository;
    }

    public String saveCollectionArea(CollectionArea collectionArea) {
        collectionAreaRepository.save(collectionArea);
        return "Collection area saved successfully";
    }

    public List<CollectionArea> getAllCollectionAreas() {
        return collectionAreaRepository.findAll();
    }

    public Optional<CollectionArea> getCollectionAreaById(UUID id) {
        return collectionAreaRepository.findById(id);
    }

    public List<CollectionArea> findFreeCollectionAreas() {
        return collectionAreaRepository.findFreeCollectionAreas();
    }

    public String updateCollectionArea(UUID id, CollectionArea updatedCollectionArea) {
        Optional<CollectionArea> existingCollectionArea =
                collectionAreaRepository.findById(id);

        if (existingCollectionArea.isPresent()) {
            CollectionArea collectionArea = existingCollectionArea.get();

            collectionArea.setAreaNumber(updatedCollectionArea.getAreaNumber());
            collectionArea.setName(updatedCollectionArea.getName());

            collectionAreaRepository.save(collectionArea);

            return "Collection area updated successfully";
        }

        return "The collection area with that id does not exist";
    }

    public String deleteCollectionArea(UUID id) {
        Optional<CollectionArea> existingCollectionArea =
                collectionAreaRepository.findById(id);

        if (existingCollectionArea.isPresent()) {
            collectionAreaRepository.delete(existingCollectionArea.get());
            return "Collection area deleted successfully";
        }

        return "The collection area with that id does not exist";
    }
}