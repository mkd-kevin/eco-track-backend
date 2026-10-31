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

import com.ecotrack.domain.WasteType;
import com.ecotrack.service.WasteTypeService;

@RestController
@RequestMapping("/api/waste-types")
public class WasteTypeController {

    private final WasteTypeService wasteTypeService;

    public WasteTypeController(WasteTypeService wasteTypeService) {
        this.wasteTypeService = wasteTypeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveWasteType(@RequestBody WasteType wasteType) {
        String message = wasteTypeService.saveWasteType(wasteType);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllWasteTypes() {
        return ResponseEntity.ok(wasteTypeService.getAllWasteTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWasteTypeById(@PathVariable UUID id) {

        Optional<WasteType> wasteType = wasteTypeService.getWasteTypeById(id);

        if (wasteType.isPresent()) {
            return ResponseEntity.ok(wasteType.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The waste type with that id does not exist");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateWasteType(
            @PathVariable UUID id,
            @RequestBody WasteType wasteType) {

        String message = wasteTypeService.updateWasteType(id, wasteType);

        if (message.contains("successfully")) {
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWasteType(@PathVariable UUID id) {

        String message = wasteTypeService.deleteWasteType(id);

        if (message.contains("successfully")) {
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}