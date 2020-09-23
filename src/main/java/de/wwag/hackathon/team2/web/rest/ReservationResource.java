package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.service.dto.DailyReservationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reservation")
public class ReservationResource {

    @GetMapping
    public ResponseEntity<DailyReservationDTO> getReservations(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return null;
    }

}
