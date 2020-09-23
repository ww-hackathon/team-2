package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.domain.DailyReservation;
import de.wwag.hackathon.team2.service.dto.DailyReservationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/reservations")
public class UserSettingsResource {

    @GetMapping
    public ResponseEntity<DailyReservationDTO> getDailyReservation() {
        return null;
    }

    @PostMapping
    public ResponseEntity<DailyReservationDTO> postDailyReservation(DailyReservationDTO dailyReservation) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DailyReservationDTO> getDailyReservationById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DailyReservationDTO> deleteDailyReservationById(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyReservationDTO> putDailyReservationById(@PathVariable Long id) {
        return null;
    }

}
