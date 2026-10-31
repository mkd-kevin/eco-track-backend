package com.ecotrack.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecotrack.domain.WasteType;
import com.ecotrack.repository.WasteTypeRepository;

@Service
public class WasteTypeService {

    private final WasteTypeRepository wasteTypeRepository;

    public WasteTypeService(WasteTypeRepository wasteTypeRepository) {
        this.wasteTypeRepository = wasteTypeRepository;
    }

    public String saveWasteType(WasteType wasteType) {
        wasteTypeRepository.save(wasteType);
        return "Waste type saved successfully";
    }

    public List<WasteType> getAllWasteTypes() {
        return wasteTypeRepository.findAll();
    }

    public Optional<WasteType> getWasteTypeById(UUID id) {
        return wasteTypeRepository.findById(id);
    }

    public List<WasteType> findUnusedWasteTypes() {
        return wasteTypeRepository.findUnusedWasteTypes();
    }

    public String updateWasteType(UUID id, WasteType updatedWasteType) {
        Optional<WasteType> existingWasteType =
                wasteTypeRepository.findById(id);

        if (existingWasteType.isPresent()) {
            WasteType wasteType = existingWasteType.get();

            wasteType.setName(updatedWasteType.getName());

            wasteTypeRepository.save(wasteType);

            return "Waste type updated successfully";
        }

        return "The waste type with that id does not exist";
    }

    public String deleteWasteType(UUID id) {
        Optional<WasteType> existingWasteType =
                wasteTypeRepository.findById(id);

        if (existingWasteType.isPresent()) {
            wasteTypeRepository.delete(existingWasteType.get());

            return "Waste type deleted successfully";
        }

        return "The waste type with that id does not exist";
    }
}