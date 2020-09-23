package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.WwHackathonTeam2App;
import de.wwag.hackathon.team2.domain.Deskgroup;
import de.wwag.hackathon.team2.repository.DeskgroupRepository;
import de.wwag.hackathon.team2.service.DeskgroupService;
import de.wwag.hackathon.team2.service.dto.DeskgroupDTO;
import de.wwag.hackathon.team2.service.mapper.DeskgroupMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DeskgroupResource} REST controller.
 */
@SpringBootTest(classes = WwHackathonTeam2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeskgroupResourceIT {

    private static final Integer DEFAULT_SEATS = 1;
    private static final Integer UPDATED_SEATS = 2;

    private static final Integer DEFAULT_IDENTIFIER = 1;
    private static final Integer UPDATED_IDENTIFIER = 2;

    @Autowired
    private DeskgroupRepository deskgroupRepository;

    @Autowired
    private DeskgroupMapper deskgroupMapper;

    @Autowired
    private DeskgroupService deskgroupService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeskgroupMockMvc;

    private Deskgroup deskgroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deskgroup createEntity(EntityManager em) {
        Deskgroup deskgroup = new Deskgroup()
            .seats(DEFAULT_SEATS)
            .identifier(DEFAULT_IDENTIFIER);
        return deskgroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deskgroup createUpdatedEntity(EntityManager em) {
        Deskgroup deskgroup = new Deskgroup()
            .seats(UPDATED_SEATS)
            .identifier(UPDATED_IDENTIFIER);
        return deskgroup;
    }

    @BeforeEach
    public void initTest() {
        deskgroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeskgroup() throws Exception {
        int databaseSizeBeforeCreate = deskgroupRepository.findAll().size();
        // Create the Deskgroup
        DeskgroupDTO deskgroupDTO = deskgroupMapper.toDto(deskgroup);
        restDeskgroupMockMvc.perform(post("/api/deskgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deskgroupDTO)))
            .andExpect(status().isCreated());

        // Validate the Deskgroup in the database
        List<Deskgroup> deskgroupList = deskgroupRepository.findAll();
        assertThat(deskgroupList).hasSize(databaseSizeBeforeCreate + 1);
        Deskgroup testDeskgroup = deskgroupList.get(deskgroupList.size() - 1);
        assertThat(testDeskgroup.getSeats()).isEqualTo(DEFAULT_SEATS);
        assertThat(testDeskgroup.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createDeskgroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deskgroupRepository.findAll().size();

        // Create the Deskgroup with an existing ID
        deskgroup.setId(1L);
        DeskgroupDTO deskgroupDTO = deskgroupMapper.toDto(deskgroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeskgroupMockMvc.perform(post("/api/deskgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deskgroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Deskgroup in the database
        List<Deskgroup> deskgroupList = deskgroupRepository.findAll();
        assertThat(deskgroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSeatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = deskgroupRepository.findAll().size();
        // set the field null
        deskgroup.setSeats(null);

        // Create the Deskgroup, which fails.
        DeskgroupDTO deskgroupDTO = deskgroupMapper.toDto(deskgroup);


        restDeskgroupMockMvc.perform(post("/api/deskgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deskgroupDTO)))
            .andExpect(status().isBadRequest());

        List<Deskgroup> deskgroupList = deskgroupRepository.findAll();
        assertThat(deskgroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = deskgroupRepository.findAll().size();
        // set the field null
        deskgroup.setIdentifier(null);

        // Create the Deskgroup, which fails.
        DeskgroupDTO deskgroupDTO = deskgroupMapper.toDto(deskgroup);


        restDeskgroupMockMvc.perform(post("/api/deskgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deskgroupDTO)))
            .andExpect(status().isBadRequest());

        List<Deskgroup> deskgroupList = deskgroupRepository.findAll();
        assertThat(deskgroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeskgroups() throws Exception {
        // Initialize the database
        deskgroupRepository.saveAndFlush(deskgroup);

        // Get all the deskgroupList
        restDeskgroupMockMvc.perform(get("/api/deskgroups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deskgroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].seats").value(hasItem(DEFAULT_SEATS)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)));
    }
    
    @Test
    @Transactional
    public void getDeskgroup() throws Exception {
        // Initialize the database
        deskgroupRepository.saveAndFlush(deskgroup);

        // Get the deskgroup
        restDeskgroupMockMvc.perform(get("/api/deskgroups/{id}", deskgroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deskgroup.getId().intValue()))
            .andExpect(jsonPath("$.seats").value(DEFAULT_SEATS))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER));
    }
    @Test
    @Transactional
    public void getNonExistingDeskgroup() throws Exception {
        // Get the deskgroup
        restDeskgroupMockMvc.perform(get("/api/deskgroups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeskgroup() throws Exception {
        // Initialize the database
        deskgroupRepository.saveAndFlush(deskgroup);

        int databaseSizeBeforeUpdate = deskgroupRepository.findAll().size();

        // Update the deskgroup
        Deskgroup updatedDeskgroup = deskgroupRepository.findById(deskgroup.getId()).get();
        // Disconnect from session so that the updates on updatedDeskgroup are not directly saved in db
        em.detach(updatedDeskgroup);
        updatedDeskgroup
            .seats(UPDATED_SEATS)
            .identifier(UPDATED_IDENTIFIER);
        DeskgroupDTO deskgroupDTO = deskgroupMapper.toDto(updatedDeskgroup);

        restDeskgroupMockMvc.perform(put("/api/deskgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deskgroupDTO)))
            .andExpect(status().isOk());

        // Validate the Deskgroup in the database
        List<Deskgroup> deskgroupList = deskgroupRepository.findAll();
        assertThat(deskgroupList).hasSize(databaseSizeBeforeUpdate);
        Deskgroup testDeskgroup = deskgroupList.get(deskgroupList.size() - 1);
        assertThat(testDeskgroup.getSeats()).isEqualTo(UPDATED_SEATS);
        assertThat(testDeskgroup.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingDeskgroup() throws Exception {
        int databaseSizeBeforeUpdate = deskgroupRepository.findAll().size();

        // Create the Deskgroup
        DeskgroupDTO deskgroupDTO = deskgroupMapper.toDto(deskgroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeskgroupMockMvc.perform(put("/api/deskgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deskgroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Deskgroup in the database
        List<Deskgroup> deskgroupList = deskgroupRepository.findAll();
        assertThat(deskgroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeskgroup() throws Exception {
        // Initialize the database
        deskgroupRepository.saveAndFlush(deskgroup);

        int databaseSizeBeforeDelete = deskgroupRepository.findAll().size();

        // Delete the deskgroup
        restDeskgroupMockMvc.perform(delete("/api/deskgroups/{id}", deskgroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deskgroup> deskgroupList = deskgroupRepository.findAll();
        assertThat(deskgroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
