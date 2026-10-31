
package com.ecotrack.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrack.domain.Resident;
import com.ecotrack.service.ResidentService;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveResident(@RequestBody Resident resident) {
        String message = residentService.saveResident(resident);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllResidents() {
        return ResponseEntity.ok(residentService.getAllResidents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResidentById(@PathVariable UUID id) {
        Optional<Resident> resident = residentService.getResidentById(id);

        if (resident.isPresent()) {
            return ResponseEntity.ok(resident.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The resident with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateResident(
            @PathVariable UUID id,
            @RequestBody Resident updatedResident) {

        String message = residentService.updateResident(id, updatedResident);

        if (message.contains("successfully")) {
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteResident(@PathVariable UUID id) {
        String message = residentService.deleteResident(id);

        if (message.contains("successfully")) {
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
