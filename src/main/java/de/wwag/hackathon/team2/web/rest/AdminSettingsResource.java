package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.domain.DeskgroupThreshold;
import de.wwag.hackathon.team2.domain.enumeration.DeskgroupThresholdValues;
import de.wwag.hackathon.team2.repository.DeskgroupThresholdRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequestMapping("/api/admin")
public class AdminSettingsResource {

    private final DeskgroupThresholdRepository deskgroupThresholdRepository;

    public AdminSettingsResource(DeskgroupThresholdRepository deskgroupThresholdRepository) {
        this.deskgroupThresholdRepository = deskgroupThresholdRepository;
    }

    @GetMapping("/threshold")
    public ResponseEntity<DeskgroupThreshold> getDeskgroupThreshold() {
        return ResponseEntity.ok(deskgroupThresholdRepository.findAll().get(0));
    }

    @PutMapping("/threshold")
    public ResponseEntity<DeskgroupThresholdValues> putDeskgroupThreshold(@RequestBody DeskgroupThresholdValues newThreshold) {
        deskgroupThresholdRepository.findAll().get(0).setThreshold(newThreshold.getPercentage());
        return ResponseEntity.noContent().build();
    }

}
