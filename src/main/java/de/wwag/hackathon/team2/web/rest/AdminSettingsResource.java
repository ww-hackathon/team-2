package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.service.dto.DeskgroupThresholdValues;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminSettingsResource {

    @GetMapping("/threshold")
    public ResponseEntity<DeskgroupThresholdValues> getDeskgroupThreshold() {
        return null;
    }

    @PutMapping("/threshold")
    public ResponseEntity<DeskgroupThresholdValues> putDeskgroupThreshold(DeskgroupThresholdValues newThreshold) {
        return null;
    }

}
