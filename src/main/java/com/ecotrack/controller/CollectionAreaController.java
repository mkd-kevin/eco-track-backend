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
import org.springframework.web.bind.annotation.RestController;

import com.ecotrack.domain.CollectionArea;
import com.ecotrack.service.CollectionAreaService;

@RestController
@RequestMapping("/api/collection-areas")
public class CollectionAreaController {

    private final CollectionAreaService collectionAreaService;

    public CollectionAreaController(CollectionAreaService collectionAreaService) {
        this.collectionAreaService = collectionAreaService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveCollectionArea(@RequestBody CollectionArea collectionArea) {
        String message = collectionAreaService.saveCollectionArea(collectionArea);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCollectionAreas() {
        return ResponseEntity.ok(collectionAreaService.getAllCollectionAreas());
    }

    @GetMapping("/free")
    public ResponseEntity<List<CollectionArea>> getFreeCollectionAreas() {
        return ResponseEntity.ok(collectionAreaService.findFreeCollectionAreas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollectionAreaById(@PathVariable UUID id) {
        Optional<CollectionArea> collectionArea =
                collectionAreaService.getCollectionAreaById(id);

        if (collectionArea.isPresent()) {
            return ResponseEntity.ok(collectionArea.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The collection area with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCollectionArea(
            @PathVariable UUID id,
            @RequestBody CollectionArea collectionArea) {

        String message = collectionAreaService.updateCollectionArea(id, collectionArea);

        if (message.contains("successfully")) {
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCollectionArea(@PathVariable UUID id) {
        String message = collectionAreaService.deleteCollectionArea(id);

        if (message.contains("successfully")) {
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}