package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.service.UserSettingsService;
import de.wwag.hackathon.team2.service.dto.DailyReservationDTO;
import de.wwag.hackathon.team2.service.mapper.DailyReservationMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<DailyReservationDTO>> getDailyReservation() {
        return ResponseEntity.ok(dailyReservationMapper.toDto(userSettingsService.getAllDailyReservationsForUser()));
    }

    @PostMapping
    public ResponseEntity<DailyReservationDTO> postDailyReservation(@RequestBody DailyReservationDTO dailyReservationDTO) {
        return ResponseEntity.ok(dailyReservationMapper.toDto(userSettingsService.addDailyReservation(dailyReservationMapper.toEntity(dailyReservationDTO))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DailyReservationDTO> getDailyReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(dailyReservationMapper.toDto(userSettingsService.getDailyReservationById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDailyReservationById(@PathVariable Long id) {

        userSettingsService.deleteDailyReservationById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyReservationDTO> putDailyReservationById(@PathVariable Long id, @RequestBody DailyReservationDTO updatedDailyReservationDTO) {
        return ResponseEntity.ok(dailyReservationMapper.toDto(
            userSettingsService.updateDailyReservationById(id, dailyReservationMapper.toEntity(updatedDailyReservationDTO))));
    }

}
