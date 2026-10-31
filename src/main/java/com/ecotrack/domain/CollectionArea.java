
package com.ecotrack.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "collection_area")
public class CollectionArea {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int areaNumber;

    private String name;

    public CollectionArea() {
    }

    public CollectionArea(int areaNumber, String name) {
        this.areaNumber = areaNumber;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public int getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(int areaNumber) {
        this.areaNumber = areaNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
