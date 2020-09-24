package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.domain.DailyReservation;
import de.wwag.hackathon.team2.service.UserSettingsService;
import de.wwag.hackathon.team2.service.dto.DailyReservationDTO;
import de.wwag.hackathon.team2.service.mapper.DailyReservationMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/reservations")
public class UserSettingsResource {

    private final UserSettingsService userSettingsService;

    private final DailyReservationMapper dailyReservationMapper;

    public UserSettingsResource(UserSettingsService userSettingsService, DailyReservationMapper dailyReservationMapper) {
        this.userSettingsService = userSettingsService;
        this.dailyReservationMapper = dailyReservationMapper;
    }

    @GetMapping
    public ResponseEntity<DailyReservationDTO> getDailyReservation() {

        userSettingsService.getAllDailyReservationsForUser();

        return null;
    }

    @PostMapping
    public ResponseEntity<DailyReservationDTO> postDailyReservation(@RequestParam DailyReservationDTO dailyReservationDTO) {

        DailyReservation dailyReservation = dailyReservationMapper.toEntity(dailyReservationDTO);
        userSettingsService.addDailyReservation(dailyReservation);

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DailyReservationDTO> getDailyReservationById(@PathVariable Long id) {

        DailyReservation dr = userSettingsService.getDailyReservationById(id);
        DailyReservationDTO drdto = dailyReservationMapper.toDto(dr);

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDailyReservationById(@PathVariable Long id) {

        userSettingsService.deleteDailyReservationById(id);

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyReservationDTO> putDailyReservationById(@PathVariable Long id, @RequestParam DailyReservationDTO updatedDailyReservationDTO) {

        DailyReservation updatedDailyReservation = dailyReservationMapper.toEntity(updatedDailyReservationDTO);
        userSettingsService.updateDailyReservationById(id, updatedDailyReservation);

        return null;
    }

}
