package com.ecotrack.controller;

import java.util.List;
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