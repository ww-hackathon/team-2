package de.wwag.hackathon.team2.service;

import de.wwag.hackathon.team2.domain.DailyReservation;
import de.wwag.hackathon.team2.domain.Deskgroup;
import de.wwag.hackathon.team2.repository.DailyReservationRepository;
import de.wwag.hackathon.team2.repository.DeskgroupRepository;
import de.wwag.hackathon.team2.service.dto.DetailedDeskgroupDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import de.wwag.hackathon.team2.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserSettingsService {
    private final Logger log = LoggerFactory.getLogger(UserSettingsService.class);

    private final DailyReservationRepository dailyReservationRepository;
    private final DeskgroupRepository deskgroupRepository;
    private final BuildingMapper buildingMapper;
    private final FloorMapper floorMapper;
    private final WingMapper wingMapper;
    private final DeskgroupMapper deskgroupMapper;

    public UserSettingsService(UserService userService, DailyReservationRepository dailyReservationRepository, DeskgroupRepository deskgroupRepository, BuildingMapper buildingMapper, FloorMapper floorMapper, WingMapper wingMapper, DeskgroupMapper deskgroupMapper) {
        this.dailyReservationRepository = dailyReservationRepository;
        this.deskgroupRepository = deskgroupRepository;
        this.buildingMapper = buildingMapper;
        this.floorMapper = floorMapper;
        this.wingMapper = wingMapper;
        this.deskgroupMapper = deskgroupMapper;
    }

    public List<DetailedDeskgroupDTO> getAllDailyReservationsForUser() {
        List<DetailedDeskgroupDTO> detailedDeskgroupDTOS = new ArrayList<>();
        List<DailyReservation> dailyReservations = dailyReservationRepository.findByUserIsCurrentUser();
        for (DailyReservation dailyReservation : dailyReservations) {
            Deskgroup deskgroup = deskgroupRepository.findById(dailyReservation.getDeskgroup().getId()).orElseThrow(() -> new NoSuchElementException());
            detailedDeskgroupDTOS.add(new DetailedDeskgroupDTO(
                buildingMapper.toDto(deskgroup.getWing().getFloor().getBuilding()),
                floorMapper.toDto(deskgroup.getWing().getFloor()),
                wingMapper.toDto(deskgroup.getWing()),
                deskgroupMapper.toDto(deskgroup)
            ));
        }
        return detailedDeskgroupDTOS;
    }

    public DailyReservation addDailyReservation(DailyReservation dailyReservation) {
        return dailyReservationRepository.save(dailyReservation);
    }

    public DailyReservation getDailyReservationById(Long id) {
        return dailyReservationRepository.findById(id).<NoSuchElementException>orElseThrow(() -> new NoSuchElementException());
    }

    public void deleteDailyReservationById(Long id) {
        dailyReservationRepository.deleteById(id);
    }

    public DailyReservation updateDailyReservationById(Long id, DailyReservation updatedDailyReservation) {
        DailyReservation dailyReservation = dailyReservationRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        dailyReservation.setDate(updatedDailyReservation.getDate());
        dailyReservation.setDeskgroup(updatedDailyReservation.getDeskgroup());
        return dailyReservationRepository.save(dailyReservation);
    }
}
