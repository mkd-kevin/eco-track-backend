
package com.ecotrack.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "waste_record")
public class WasteRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String materialName;

    private String notes;

    private int weightKg;

    private LocalDate recordedDate;

    @ManyToOne
    @JoinColumn(name = "collection_request_id")
    private CollectionRequest collectionRequest;

    public WasteRecord() {
    }

    public WasteRecord(String materialName, String notes, int weightKg,
                       LocalDate recordedDate,
                       CollectionRequest collectionRequest) {
        this.materialName = materialName;
        this.notes = notes;
        this.weightKg = weightKg;
        this.recordedDate = recordedDate;
        this.collectionRequest = collectionRequest;
    }

    public UUID getId() {
        return id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    public LocalDate getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(LocalDate recordedDate) {
        this.recordedDate = recordedDate;
    }

    public CollectionRequest getCollectionRequest() {
        return collectionRequest;
    }

    public void setCollectionRequest(CollectionRequest collectionRequest) {
        this.collectionRequest = collectionRequest;
    }
}
