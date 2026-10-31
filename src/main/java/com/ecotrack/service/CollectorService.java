package com.ecotrack.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecotrack.domain.CollectionArea;
import com.ecotrack.domain.Collector;
import com.ecotrack.domain.WasteType;
import com.ecotrack.repository.CollectionAreaRepository;
import com.ecotrack.repository.CollectorRepository;
import com.ecotrack.repository.WasteTypeRepository;

@Service
public class CollectorService {

    private final CollectorRepository collectorRepository;
    private final CollectionAreaRepository collectionAreaRepository;
    private final WasteTypeRepository wasteTypeRepository;

    public CollectorService(
            CollectorRepository collectorRepository,
            CollectionAreaRepository collectionAreaRepository,
            WasteTypeRepository wasteTypeRepository) {

        this.collectorRepository = collectorRepository;
        this.collectionAreaRepository = collectionAreaRepository;
        this.wasteTypeRepository = wasteTypeRepository;
    }

    @Transactional
    public String saveCollector(Collector collector) {

        collector.setCollectionArea(
                findCollectionArea(collector.getCollectionArea()));

        collector.setWasteTypes(
                findWasteTypes(collector.getWasteTypes()));

        collectorRepository.save(collector);

        return "Collector saved successfully";
    }

    public List<Collector> getAllCollectors() {
        return collectorRepository.findAll();
    }

    public Optional<Collector> getCollectorById(UUID id) {
        return collectorRepository.findById(id);
    }

    public List<Collector> findCollectorsByWasteTypeName(String name) {
        return collectorRepository.findCollectorsByWasteTypeName(name);
    }

    @Transactional
    public String updateCollector(UUID id, Collector updatedCollector) {

        Optional<Collector> existingCollector =
                collectorRepository.findById(id);

        if (existingCollector.isPresent()) {

            Collector collector = existingCollector.get();

            collector.setFirstName(updatedCollector.getFirstName());
            collector.setLastName(updatedCollector.getLastName());
            collector.setPhone(updatedCollector.getPhone());

            collector.setCollectionArea(
                    findCollectionArea(updatedCollector.getCollectionArea()));

            collector.setWasteTypes(
                    findWasteTypes(updatedCollector.getWasteTypes()));

            collectorRepository.save(collector);

            return "Collector updated successfully";
        }

        return "The collector with that id does not exist";
    }

    public String deleteCollector(UUID id) {

        Optional<Collector> existingCollector =
                collectorRepository.findById(id);

        if (existingCollector.isPresent()) {

            collectorRepository.delete(existingCollector.get());

            return "Collector deleted successfully";
        }

        return "The collector with that id does not exist";
    }

    private CollectionArea findCollectionArea(CollectionArea collectionArea) {

        if (collectionArea == null) {
            return null;
        }

        if (collectionArea.getId() == null) {
            throw new IllegalArgumentException(
                    "Collection area must have an existing UUID");
        }

        return collectionAreaRepository.findById(collectionArea.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Collection area does not exist"));
    }

    private List<WasteType> findWasteTypes(List<WasteType> wasteTypes) {

        List<WasteType> existingWasteTypes = new ArrayList<>();

        if (wasteTypes == null) {
            return existingWasteTypes;
        }

        for (WasteType wasteType : wasteTypes) {

            if (wasteType == null || wasteType.getId() == null) {
                throw new IllegalArgumentException(
                        "Each waste type must have an existing UUID");
            }

            WasteType existingWasteType =
                    wasteTypeRepository.findById(wasteType.getId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Waste type does not exist"));

            existingWasteTypes.add(existingWasteType);
        }

        return existingWasteTypes;
    }
}