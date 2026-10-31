
package com.ecotrack.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecotrack.domain.Collector;

public interface CollectorRepository extends JpaRepository<Collector, UUID> {

    @Query("SELECT DISTINCT c FROM Collector c JOIN c.wasteTypes w WHERE LOWER(w.name) = LOWER(:name)")
    List<Collector> findCollectorsByWasteTypeName(@Param("name") String name);

}
