package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.service.DeskgroupService;
import de.wwag.hackathon.team2.web.rest.errors.BadRequestAlertException;
import de.wwag.hackathon.team2.service.dto.DeskgroupDTO;

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
 * REST controller for managing {@link de.wwag.hackathon.team2.domain.Deskgroup}.
 */
@RestController
@RequestMapping("/api")
public class DeskgroupResource {

    private final Logger log = LoggerFactory.getLogger(DeskgroupResource.class);

    private static final String ENTITY_NAME = "deskgroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeskgroupService deskgroupService;

    public DeskgroupResource(DeskgroupService deskgroupService) {
        this.deskgroupService = deskgroupService;
    }

    /**
     * {@code POST  /deskgroups} : Create a new deskgroup.
     *
     * @param deskgroupDTO the deskgroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deskgroupDTO, or with status {@code 400 (Bad Request)} if the deskgroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deskgroups")
    public ResponseEntity<DeskgroupDTO> createDeskgroup(@Valid @RequestBody DeskgroupDTO deskgroupDTO) throws URISyntaxException {
        log.debug("REST request to save Deskgroup : {}", deskgroupDTO);
        if (deskgroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new deskgroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeskgroupDTO result = deskgroupService.save(deskgroupDTO);
        return ResponseEntity.created(new URI("/api/deskgroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deskgroups} : Updates an existing deskgroup.
     *
     * @param deskgroupDTO the deskgroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deskgroupDTO,
     * or with status {@code 400 (Bad Request)} if the deskgroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deskgroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deskgroups")
    public ResponseEntity<DeskgroupDTO> updateDeskgroup(@Valid @RequestBody DeskgroupDTO deskgroupDTO) throws URISyntaxException {
        log.debug("REST request to update Deskgroup : {}", deskgroupDTO);
        if (deskgroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeskgroupDTO result = deskgroupService.save(deskgroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deskgroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /deskgroups} : get all the deskgroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deskgroups in body.
     */
    @GetMapping("/deskgroups")
    public List<DeskgroupDTO> getAllDeskgroups() {
        log.debug("REST request to get all Deskgroups");
        return deskgroupService.findAll();
    }

    /**
     * {@code GET  /deskgroups/:id} : get the "id" deskgroup.
     *
     * @param id the id of the deskgroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deskgroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deskgroups/{id}")
    public ResponseEntity<DeskgroupDTO> getDeskgroup(@PathVariable Long id) {
        log.debug("REST request to get Deskgroup : {}", id);
        Optional<DeskgroupDTO> deskgroupDTO = deskgroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deskgroupDTO);
    }

    /**
     * {@code DELETE  /deskgroups/:id} : delete the "id" deskgroup.
     *
     * @param id the id of the deskgroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deskgroups/{id}")
    public ResponseEntity<Void> deleteDeskgroup(@PathVariable Long id) {
        log.debug("REST request to delete Deskgroup : {}", id);
        deskgroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
