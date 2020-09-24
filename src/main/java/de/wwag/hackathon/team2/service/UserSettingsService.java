package de.wwag.hackathon.team2.service;

import de.wwag.hackathon.team2.domain.DailyReservation;
import de.wwag.hackathon.team2.repository.DailyReservationRepository;
import de.wwag.hackathon.team2.service.dto.DailyReservationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserSettingsService {

    private final Logger log = LoggerFactory.getLogger(UserSettingsService.class);

    private final DailyReservationRepository dailyReservationRepository;

    public UserSettingsService(DailyReservationRepository dailyReservationRepository) {
        this.dailyReservationRepository = dailyReservationRepository;
    }

    public List<DailyReservation> getAllDailyReservationsForUser() {

        return null;
    }

    public DailyReservation addDailyReservation(DailyReservation dailyReservation) {

        return null;
    }

    public DailyReservation getDailyReservationById(Long id) {

        return null;
    }

    public void deleteDailyReservationById(Long id) {
    }

    public DailyReservation updateDailyReservationById(Long id, DailyReservation updatedDailyReservation) {

        return null;
    }
}
