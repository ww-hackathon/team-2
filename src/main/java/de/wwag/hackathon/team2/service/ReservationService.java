package de.wwag.hackathon.team2.service;

import de.wwag.hackathon.team2.domain.DailyReservation;
import de.wwag.hackathon.team2.domain.Deskgroup;
import de.wwag.hackathon.team2.repository.*;
import de.wwag.hackathon.team2.service.dto.DetailedDeskgroupDTO;
import de.wwag.hackathon.team2.service.mapper.BuildingMapper;
import de.wwag.hackathon.team2.service.mapper.DeskgroupMapper;
import de.wwag.hackathon.team2.service.mapper.FloorMapper;
import de.wwag.hackathon.team2.service.mapper.WingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class ReservationService {

    private final Logger log = LoggerFactory.getLogger(ReservationService.class);

    private final DeskgroupRepository deskgroupRepository;
    private final DailyReservationRepository dailyReservationRepository;
    private final BuildingRepository buildingRepository;
    private final FloorRepository floorRepository;
    private final WingRepository wingRepository;
    private final DeskgroupThresholdRepository deskgroupThresholdRepository;

    private final BuildingMapper buildingMapper;
    private final FloorMapper floorMapper;
    private final WingMapper wingMapper;
    private final DeskgroupMapper deskgroupMapper;

    public ReservationService(DeskgroupRepository deskgroupRepository, DailyReservationRepository dailyReservationRepository, BuildingRepository buildingRepository, FloorRepository floorRepository, WingRepository wingRepository, DeskgroupThresholdRepository deskgroupThresholdRepository, BuildingMapper buildingMapper, FloorMapper floorMapper, WingMapper wingMapper, DeskgroupMapper deskgroupMapper) {
        this.deskgroupRepository = deskgroupRepository;
        this.dailyReservationRepository = dailyReservationRepository;
        this.buildingRepository = buildingRepository;
        this.floorRepository = floorRepository;
        this.wingRepository = wingRepository;
        this.deskgroupThresholdRepository = deskgroupThresholdRepository;
        this.buildingMapper = buildingMapper;
        this.floorMapper = floorMapper;
        this.wingMapper = wingMapper;
        this.deskgroupMapper = deskgroupMapper;
    }

    private List<Deskgroup> getFreeDeskgroupsInDateSpan(LocalDate startDate, LocalDate endDate) {
       List<DailyReservation> dailyReservations = dailyReservationRepository.findAllByDateIsBetween(startDate, endDate);
       List<Long> deskIds = getCompletlyBookedDeskgroupIds(getBookedDeskgroupsPerId(dailyReservations));
       if(deskIds.isEmpty()) {
           return deskgroupRepository.findAll();
       } else {
           return deskgroupRepository.findAllByIdIsNot(deskIds);
       }


    }

    private List<Long> getCompletlyBookedDeskgroupIds(Map<Long, Integer> map) {
        List<Long> list = new ArrayList<>();
        double threshold = deskgroupThresholdRepository.findAll().get(0).getThreshold();
        /** Falls 500er Fehler wieder auftauchen sollten:
        Iterator<Map.Entry<Long, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, Integer> entry = it.next();
            try {
                Deskgroup deskgroup = deskgroupRepository.findById(entry.getKey()).get();
                int roundedValue = (int) Math.floor(deskgroup.getSeats() * threshold);
                System.err.println("VALUES____________________" + roundedValue + " " + entry.getValue());
                if (entry.getValue() >= roundedValue) {
                    list.add(entry.getKey());
                }
            }catch (NoSuchElementException nsee) {
                log.error("Skipped empty field in deskgroup-Map.");
            }
        }**/
        if (!map.isEmpty()) {
            for (Map.Entry<Long, Integer> entry : map.entrySet()) {
                Deskgroup deskgroup = deskgroupRepository.findById(entry.getKey()).get();
                int roundedValue = (int) Math.floor(deskgroup.getSeats() * threshold);
                System.err.println("VALUES____________________" + roundedValue + " " + entry.getValue());
                if (entry.getValue() >= roundedValue) {
                    list.add(entry.getKey());
                }
            }
        }
        return list;
    }
    private Map<Long, Integer> getBookedDeskgroupsPerId(List<DailyReservation> dailyReservationList) {
        Map<Long, Integer> valueMap = new HashMap<Long, Integer>();
        for (DailyReservation reservation : dailyReservationList) {
            if (valueMap.containsKey(reservation.getDeskgroup().getId())) {
                valueMap.put(reservation.getDeskgroup().getId(), valueMap.get(reservation.getDeskgroup().getId()) + 1);
            } else {
                valueMap.put(reservation.getDeskgroup().getId(), 1);
            }
        }
        return valueMap;
    }

    public List<DetailedDeskgroupDTO> getAvailableDeskgroupsInDateSpan(LocalDate startDate, LocalDate endDate) {
        List<DetailedDeskgroupDTO> availableDeskgroupDTOS = new ArrayList<>();
        List<Deskgroup> freeDeskgroups = getFreeDeskgroupsInDateSpan(startDate, endDate);
        freeDeskgroups.forEach(deskgroup -> {
            availableDeskgroupDTOS.add(new DetailedDeskgroupDTO(
                buildingMapper.toDto(deskgroup.getWing().getFloor().getBuilding()),
                floorMapper.toDto(deskgroup.getWing().getFloor()),
                wingMapper.toDto(deskgroup.getWing()),
                deskgroupMapper.toDto(deskgroup)
            ));
        });
        return availableDeskgroupDTOS;
    }
}
