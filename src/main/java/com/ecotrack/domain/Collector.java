
package com.ecotrack.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "collector")
public class Collector {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "collection_area_id", nullable = true)
    private CollectionArea collectionArea;

    @ManyToMany
    @JoinTable(
        name = "collector_waste_type",
        joinColumns = @JoinColumn(name = "collector_id"),
        inverseJoinColumns = @JoinColumn(name = "waste_type_id")
    )
    private List<WasteType> wasteTypes = new ArrayList<>();

    public Collector() {
    }

    public Collector(String firstName, String lastName, String phone,
                     CollectionArea collectionArea) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.collectionArea = collectionArea;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CollectionArea getCollectionArea() {
        return collectionArea;
    }

    public void setCollectionArea(CollectionArea collectionArea) {
        this.collectionArea = collectionArea;
    }

    public List<WasteType> getWasteTypes() {
        return wasteTypes;
    }

    public void setWasteTypes(List<WasteType> wasteTypes) {
        this.wasteTypes = wasteTypes;
    }
}
