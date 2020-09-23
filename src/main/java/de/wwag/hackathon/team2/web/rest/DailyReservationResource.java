package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.service.DailyReservationService;
import de.wwag.hackathon.team2.web.rest.errors.BadRequestAlertException;
import de.wwag.hackathon.team2.service.dto.DailyReservationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.wwag.hackathon.team2.domain.DailyReservation}.
 */
@RestController
@RequestMapping("/api")
public class DailyReservationResource {

    private final Logger log = LoggerFactory.getLogger(DailyReservationResource.class);

    private static final String ENTITY_NAME = "dailyReservation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DailyReservationService dailyReservationService;

    public DailyReservationResource(DailyReservationService dailyReservationService) {
        this.dailyReservationService = dailyReservationService;
    }

    /**
     * {@code POST  /daily-reservations} : Create a new dailyReservation.
     *
     * @param dailyReservationDTO the dailyReservationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dailyReservationDTO, or with status {@code 400 (Bad Request)} if the dailyReservation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/daily-reservations")
    public ResponseEntity<DailyReservationDTO> createDailyReservation(@Valid @RequestBody DailyReservationDTO dailyReservationDTO) throws URISyntaxException {
        log.debug("REST request to save DailyReservation : {}", dailyReservationDTO);
        if (dailyReservationDTO.getId() != null) {
            throw new BadRequestAlertException("A new dailyReservation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyReservationDTO result = dailyReservationService.save(dailyReservationDTO);
        return ResponseEntity.created(new URI("/api/daily-reservations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /daily-reservations} : Updates an existing dailyReservation.
     *
     * @param dailyReservationDTO the dailyReservationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyReservationDTO,
     * or with status {@code 400 (Bad Request)} if the dailyReservationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dailyReservationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/daily-reservations")
    public ResponseEntity<DailyReservationDTO> updateDailyReservation(@Valid @RequestBody DailyReservationDTO dailyReservationDTO) throws URISyntaxException {
        log.debug("REST request to update DailyReservation : {}", dailyReservationDTO);
        if (dailyReservationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DailyReservationDTO result = dailyReservationService.save(dailyReservationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyReservationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /daily-reservations} : get all the dailyReservations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dailyReservations in body.
     */
    @GetMapping("/daily-reservations")
    public List<DailyReservationDTO> getAllDailyReservations() {
        log.debug("REST request to get all DailyReservations");
        return dailyReservationService.findAll();
    }

    /**
     * {@code GET  /daily-reservations/:id} : get the "id" dailyReservation.
     *
     * @param id the id of the dailyReservationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dailyReservationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/daily-reservations/{id}")
    public ResponseEntity<DailyReservationDTO> getDailyReservation(@PathVariable Long id) {
        log.debug("REST request to get DailyReservation : {}", id);
        Optional<DailyReservationDTO> dailyReservationDTO = dailyReservationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dailyReservationDTO);
    }

    /**
     * {@code DELETE  /daily-reservations/:id} : delete the "id" dailyReservation.
     *
     * @param id the id of the dailyReservationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/daily-reservations/{id}")
    public ResponseEntity<Void> deleteDailyReservation(@PathVariable Long id) {
        log.debug("REST request to delete DailyReservation : {}", id);
        dailyReservationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
