package com.ecotrack.controller;

import java.time.LocalDate;
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

import com.ecotrack.domain.CollectionRequest;
import com.ecotrack.domain.CollectionStatus;
import com.ecotrack.service.CollectionRequestService;

@RestController
@RequestMapping("/api/collection-requests")
public class CollectionRequestController {

    private final CollectionRequestService collectionRequestService;

    public CollectionRequestController(
            CollectionRequestService collectionRequestService) {
        this.collectionRequestService = collectionRequestService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveCollectionRequest(
            @RequestBody CollectionRequest collectionRequest) {

        String message = collectionRequestService
                .saveCollectionRequest(collectionRequest);

        if (message.equals("Collection request saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }

        if (message.equals("Collector already has a collection on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateCollectionRequestStatus(
            @PathVariable UUID id,
            @RequestParam CollectionStatus status) {

        String message = collectionRequestService
                .updateCollectionRequestStatus(id, status);

        if (message.equals("Collection request status updated successfully")) {
            return ResponseEntity.ok(message);
        }

        if (message.equals("The collection request with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCollectionRequests() {
        return ResponseEntity.ok(
                collectionRequestService.getAllCollectionRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollectionRequestById(
            @PathVariable UUID id) {

        Optional<CollectionRequest> collectionRequest =
                collectionRequestService.getCollectionRequestById(id);

        if (collectionRequest.isPresent()) {
            return ResponseEntity.ok(collectionRequest.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The collection request with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCollectionRequest(
            @PathVariable UUID id,
            @RequestBody CollectionRequest updatedCollectionRequest) {

        String message = collectionRequestService
                .updateCollectionRequest(id, updatedCollectionRequest);

        if (message.contains("successfully")) {
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCollectionRequest(
            @PathVariable UUID id) {

        String message = collectionRequestService.deleteCollectionRequest(id);

        if (message.equals("Collection request deleted successfully")) {
            return ResponseEntity.ok(message);
        }

        if (message.equals("That collection request has waste records, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @GetMapping("/by-status")
    public List<CollectionRequest> findRequestsByStatus(
            @RequestParam CollectionStatus status) {
        return collectionRequestService.findRequestsByStatus(status);
    }

    @GetMapping("/between")
    public List<CollectionRequest> findRequestsBetweenDates(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return collectionRequestService.findRequestsBetweenDates(startDate, endDate);
    }
}