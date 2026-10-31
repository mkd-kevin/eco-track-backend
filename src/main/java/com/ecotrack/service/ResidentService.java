package com.ecotrack.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecotrack.domain.Resident;
import com.ecotrack.repository.CollectorRepository;
import com.ecotrack.repository.ResidentRepository;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final CollectorRepository collectorRepository;

    public ResidentService(
            ResidentRepository residentRepository,
            CollectorRepository collectorRepository) {
        this.residentRepository = residentRepository;
        this.collectorRepository = collectorRepository;
    }

    public String saveResident(Resident resident) {
        residentRepository.save(resident);
        return "Resident saved successfully";
    }

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    public Optional<Resident> getResidentById(UUID id) {
        return residentRepository.findById(id);
    }

    public Optional<List<Resident>> findResidentsByCollectorId(UUID collectorId) {
        if (!collectorRepository.existsById(collectorId)) {
            return Optional.empty();
        }

        return Optional.of(
                residentRepository.findResidentsByCollectorId(collectorId)
        );
    }

    public String updateResident(UUID id, Resident updatedResident) {
        Optional<Resident> existingResident =
                residentRepository.findById(id);

        if (existingResident.isPresent()) {
            Resident resident = existingResident.get();

            resident.setFirstName(updatedResident.getFirstName());
            resident.setLastName(updatedResident.getLastName());
            resident.setHouseNumber(updatedResident.getHouseNumber());
            resident.setPhone(updatedResident.getPhone());
            resident.setEmail(updatedResident.getEmail());

            residentRepository.save(resident);

            return "Resident updated successfully";
        }

        return "The resident with that id does not exist";
    }

    public String deleteResident(UUID id) {
        Optional<Resident> existingResident =
                residentRepository.findById(id);

        if (existingResident.isPresent()) {
            residentRepository.delete(existingResident.get());
            return "Resident deleted successfully";
        }

        return "The resident with that id does not exist";
    }

    public List<Resident> findResidentsByLastName(String lastName) {
        return residentRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
}