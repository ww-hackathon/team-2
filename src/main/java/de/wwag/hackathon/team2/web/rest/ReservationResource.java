package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.service.ReservationService;
import de.wwag.hackathon.team2.service.dto.AvailableDeskgroupDTO;
import de.wwag.hackathon.team2.service.mapper.DeskgroupMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationResource {

    private final DeskgroupMapper deskgroupMapper;

    private final ReservationService reservationService;

    public ReservationResource(DeskgroupMapper deskgroupMapper, ReservationService reservationService) {
        this.deskgroupMapper = deskgroupMapper;
        this.reservationService = reservationService;
    }


    @GetMapping
    public ResponseEntity<List<AvailableDeskgroupDTO>> getReservations(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {


        return ResponseEntity.ok(reservationService.getAvailableDeskgroupsInDateSpan(startDate, endDate));
    }

}
