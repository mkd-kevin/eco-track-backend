
package com.ecotrack.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrack.domain.Collector;
import com.ecotrack.service.CollectorService;

@RestController
@RequestMapping("/api/collectors")
public class CollectorController {

    private final CollectorService collectorService;

    public CollectorController(CollectorService collectorService) {
        this.collectorService = collectorService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveCollector(@RequestBody Collector collector) {
        String message = collectorService.saveCollector(collector);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCollectors() {
        return ResponseEntity.ok(collectorService.getAllCollectors());
    }

    @GetMapping("/by-waste-type")
    public ResponseEntity<List<Collector>> findCollectorsByWasteTypeName(
            @RequestParam String name) {
        return ResponseEntity.ok(
                collectorService.findCollectorsByWasteTypeName(name));
    }

    @PutMapping("/{collectorId}/waste-types/{wasteTypeId}")
    public ResponseEntity<String> addWasteTypeToCollector(
            @PathVariable UUID collectorId,
            @PathVariable UUID wasteTypeId) {

        String message = collectorService.addWasteTypeToCollector(
                collectorId, wasteTypeId);

        if (message.equals("Waste type added to the collector")) {
            return ResponseEntity.ok(message);
        }

        if (message.equals("The collector with that id does not exist")
                || message.equals("That waste type does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @PatchMapping("/{collectorId}/collection-area")
    public ResponseEntity<String> changeCollectorCollectionArea(
            @PathVariable UUID collectorId,
            @RequestParam int areaNumber) {

        String message = collectorService.changeCollectorCollectionArea(
                collectorId, areaNumber);

        if (message.equals("Collection area changed successfully")) {
            return ResponseEntity.ok(message);
        }

        if (message.equals("The collector with that id does not exist")
                || message.equals("The collection area with that areaNumber does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollectorById(@PathVariable UUID id) {
        Optional<Collector> collector = collectorService.getCollectorById(id);

        if (collector.isPresent()) {
            return ResponseEntity.ok(collector.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The collector with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCollector(
            @PathVariable UUID id,
            @RequestBody Collector collector) {

        String message = collectorService.updateCollector(id, collector);

        if (message.contains("successfully")) {
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCollector(@PathVariable UUID id) {
        String message = collectorService.deleteCollector(id);

        if (message.contains("successfully")) {
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
