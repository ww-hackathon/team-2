package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.service.ReservationService;
import de.wwag.hackathon.team2.service.dto.DetailedDeskgroupDTO;
import de.wwag.hackathon.team2.service.mapper.DeskgroupMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public ResponseEntity<List<DetailedDeskgroupDTO>> getReservations(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam(required = false) List<Integer> buildings) {
        List<Integer> dummy = new ArrayList<>();
        return ResponseEntity.ok(reservationService.getAvailableDeskgroupsInDateSpan(startDate, endDate, (buildings == null) ? dummy : buildings));
    }

}
