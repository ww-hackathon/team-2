package de.wwag.hackathon.team2.service;

import de.wwag.hackathon.team2.domain.DailyReservation;
import de.wwag.hackathon.team2.domain.Deskgroup;
import de.wwag.hackathon.team2.repository.*;
import de.wwag.hackathon.team2.service.dto.AvailableDeskgroupDTO;
import de.wwag.hackathon.team2.service.mapper.BuildingMapper;
import de.wwag.hackathon.team2.service.mapper.DeskgroupMapper;
import de.wwag.hackathon.team2.service.mapper.FloorMapper;
import de.wwag.hackathon.team2.service.mapper.WingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReservationService {

    private final DeskgroupRepository deskgroupRepository;
    private final DailyReservationRepository dailyReservationRepository;
    private final BuildingRepository buildingRepository;
    private final FloorRepository floorRepository;
    private final WingRepository wingRepository;

    private final BuildingMapper buildingMapper;
    private final FloorMapper floorMapper;
    private final WingMapper wingMapper;
    private final DeskgroupMapper deskgroupMapper;

    public ReservationService(DeskgroupRepository deskgroupRepository, DailyReservationRepository dailyReservationRepository, BuildingRepository buildingRepository, FloorRepository floorRepository, WingRepository wingRepository, BuildingMapper buildingMapper, FloorMapper floorMapper, WingMapper wingMapper, DeskgroupMapper deskgroupMapper) {
        this.deskgroupRepository = deskgroupRepository;
        this.dailyReservationRepository = dailyReservationRepository;
        this.buildingRepository = buildingRepository;
        this.floorRepository = floorRepository;
        this.wingRepository = wingRepository;
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
           return deskgroupRepository.findAllByIdIn(deskIds);
       }


    }

    private List<Long> getCompletlyBookedDeskgroupIds(Map<Long, Integer> map) {
        List<Long> list = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : map.entrySet()) {
            Deskgroup deskgroup = deskgroupRepository.findById(entry.getKey()).get();
            // TODO add Threshhold
            if (entry.getValue() >= deskgroup.getSeats()) {
                list.add(entry.getKey());
            }
        }
        return list;
    }
    private Map<Long, Integer> getBookedDeskgroupsPerId(List<DailyReservation> dailyReservationList) {
        Map<Long, Integer> valueMap = new HashMap<Long, Integer>();
        for (DailyReservation reservation : dailyReservationList) {
            if (valueMap.containsKey(reservation.getId())) {
                valueMap.put(reservation.getId(), valueMap.get(reservation.getId()) + 1);
            } else {
                valueMap.put(reservation.getId(), 1);
            }
        }
        return valueMap;
    }

    public List<AvailableDeskgroupDTO> getAvailableDeskgroupsInDateSpan(LocalDate startDate, LocalDate endDate) {
        List<AvailableDeskgroupDTO> availableDeskgroupDTOS = new ArrayList<>();
        List<Deskgroup> freeDeskgroups = getFreeDeskgroupsInDateSpan(startDate, endDate);
        freeDeskgroups.forEach(deskgroup -> {
            availableDeskgroupDTOS.add(new AvailableDeskgroupDTO (
                buildingMapper.toDto(deskgroup.getWing().getFloor().getBuilding()),
                floorMapper.toDto(deskgroup.getWing().getFloor()),
                wingMapper.toDto(deskgroup.getWing()),
                deskgroupMapper.toDto(deskgroup)
            ));
        });
        return availableDeskgroupDTOS;
    }
}
