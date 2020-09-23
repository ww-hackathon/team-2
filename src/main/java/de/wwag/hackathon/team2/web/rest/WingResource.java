package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.service.WingService;
import de.wwag.hackathon.team2.web.rest.errors.BadRequestAlertException;
import de.wwag.hackathon.team2.service.dto.WingDTO;

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
 * REST controller for managing {@link de.wwag.hackathon.team2.domain.Wing}.
 */
@RestController
@RequestMapping("/api")
public class WingResource {

    private final Logger log = LoggerFactory.getLogger(WingResource.class);

    private static final String ENTITY_NAME = "wing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WingService wingService;

    public WingResource(WingService wingService) {
        this.wingService = wingService;
    }

    /**
     * {@code POST  /wings} : Create a new wing.
     *
     * @param wingDTO the wingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wingDTO, or with status {@code 400 (Bad Request)} if the wing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wings")
    public ResponseEntity<WingDTO> createWing(@Valid @RequestBody WingDTO wingDTO) throws URISyntaxException {
        log.debug("REST request to save Wing : {}", wingDTO);
        if (wingDTO.getId() != null) {
            throw new BadRequestAlertException("A new wing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WingDTO result = wingService.save(wingDTO);
        return ResponseEntity.created(new URI("/api/wings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wings} : Updates an existing wing.
     *
     * @param wingDTO the wingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wingDTO,
     * or with status {@code 400 (Bad Request)} if the wingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wings")
    public ResponseEntity<WingDTO> updateWing(@Valid @RequestBody WingDTO wingDTO) throws URISyntaxException {
        log.debug("REST request to update Wing : {}", wingDTO);
        if (wingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WingDTO result = wingService.save(wingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wings} : get all the wings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wings in body.
     */
    @GetMapping("/wings")
    public List<WingDTO> getAllWings() {
        log.debug("REST request to get all Wings");
        return wingService.findAll();
    }

    /**
     * {@code GET  /wings/:id} : get the "id" wing.
     *
     * @param id the id of the wingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wings/{id}")
    public ResponseEntity<WingDTO> getWing(@PathVariable Long id) {
        log.debug("REST request to get Wing : {}", id);
        Optional<WingDTO> wingDTO = wingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wingDTO);
    }

    /**
     * {@code DELETE  /wings/:id} : delete the "id" wing.
     *
     * @param id the id of the wingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wings/{id}")
    public ResponseEntity<Void> deleteWing(@PathVariable Long id) {
        log.debug("REST request to delete Wing : {}", id);
        wingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
