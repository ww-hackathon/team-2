package de.wwag.hackathon.team2.service;

import de.wwag.hackathon.team2.domain.DailyReservation;
import de.wwag.hackathon.team2.domain.User;
import de.wwag.hackathon.team2.repository.DailyReservationRepository;
import de.wwag.hackathon.team2.web.rest.errors.BadRequestAlertException;
import java.util.List;
import java.util.NoSuchElementException;
import org.h2.message.DbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserSettingsService {
  private final Logger log = LoggerFactory.getLogger(UserSettingsService.class);

  private final DailyReservationRepository dailyReservationRepository;

  public UserSettingsService(UserService userService, DailyReservationRepository dailyReservationRepository) {
    this.dailyReservationRepository = dailyReservationRepository;
  }

  public List<DailyReservation> getAllDailyReservationsForUser() {
    return dailyReservationRepository.findByUserIsCurrentUser();
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
