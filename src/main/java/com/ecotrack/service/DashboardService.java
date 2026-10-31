
package com.ecotrack.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecotrack.domain.CollectionStatus;
import com.ecotrack.repository.CollectionRequestRepository;
import com.ecotrack.repository.CollectorRepository;
import com.ecotrack.repository.ResidentRepository;
import com.ecotrack.repository.WasteRecordRepository;

@Service
public class DashboardService {

    private final ResidentRepository residentRepository;
    private final CollectorRepository collectorRepository;
    private final CollectionAreaService collectionAreaService;
    private final CollectionRequestRepository collectionRequestRepository;
    private final WasteRecordRepository wasteRecordRepository;

    public DashboardService(
            ResidentRepository residentRepository,
            CollectorRepository collectorRepository,
            CollectionAreaService collectionAreaService,
            CollectionRequestRepository collectionRequestRepository,
            WasteRecordRepository wasteRecordRepository) {

        this.residentRepository = residentRepository;
        this.collectorRepository = collectorRepository;
        this.collectionAreaService = collectionAreaService;
        this.collectionRequestRepository = collectionRequestRepository;
        this.wasteRecordRepository = wasteRecordRepository;
    }

    public Map<String, Long> getDashboardSummary() {

        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("residents", residentRepository.count());

        summary.put("collectors", collectorRepository.count());

        summary.put("freeAreas",
                (long) collectionAreaService.findFreeCollectionAreas().size());

        summary.put("requestsToday",
                collectionRequestRepository.countByRequestDate(LocalDate.now()));

        summary.put("submitted",
                collectionRequestRepository.countByStatus(CollectionStatus.SUBMITTED));

        summary.put("wasteRecords", wasteRecordRepository.count());

        return summary;
    }
}
