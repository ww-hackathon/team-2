package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.WwHackathonTeam2App;
import de.wwag.hackathon.team2.domain.Wing;
import de.wwag.hackathon.team2.repository.WingRepository;
import de.wwag.hackathon.team2.service.WingService;
import de.wwag.hackathon.team2.service.dto.WingDTO;
import de.wwag.hackathon.team2.service.mapper.WingMapper;

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

import de.wwag.hackathon.team2.domain.enumeration.WingIdentifier;
/**
 * Integration tests for the {@link WingResource} REST controller.
 */
@SpringBootTest(classes = WwHackathonTeam2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class WingResourceIT {

    private static final WingIdentifier DEFAULT_IDENTIFIER = WingIdentifier.A;
    private static final WingIdentifier UPDATED_IDENTIFIER = WingIdentifier.B;

    @Autowired
    private WingRepository wingRepository;

    @Autowired
    private WingMapper wingMapper;

    @Autowired
    private WingService wingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWingMockMvc;

    private Wing wing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wing createEntity(EntityManager em) {
        Wing wing = new Wing()
            .identifier(DEFAULT_IDENTIFIER);
        return wing;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wing createUpdatedEntity(EntityManager em) {
        Wing wing = new Wing()
            .identifier(UPDATED_IDENTIFIER);
        return wing;
    }

    @BeforeEach
    public void initTest() {
        wing = createEntity(em);
    }

    @Test
    @Transactional
    public void createWing() throws Exception {
        int databaseSizeBeforeCreate = wingRepository.findAll().size();
        // Create the Wing
        WingDTO wingDTO = wingMapper.toDto(wing);
        restWingMockMvc.perform(post("/api/wings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wingDTO)))
            .andExpect(status().isCreated());

        // Validate the Wing in the database
        List<Wing> wingList = wingRepository.findAll();
        assertThat(wingList).hasSize(databaseSizeBeforeCreate + 1);
        Wing testWing = wingList.get(wingList.size() - 1);
        assertThat(testWing.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createWingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wingRepository.findAll().size();

        // Create the Wing with an existing ID
        wing.setId(1L);
        WingDTO wingDTO = wingMapper.toDto(wing);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWingMockMvc.perform(post("/api/wings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wing in the database
        List<Wing> wingList = wingRepository.findAll();
        assertThat(wingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = wingRepository.findAll().size();
        // set the field null
        wing.setIdentifier(null);

        // Create the Wing, which fails.
        WingDTO wingDTO = wingMapper.toDto(wing);


        restWingMockMvc.perform(post("/api/wings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wingDTO)))
            .andExpect(status().isBadRequest());

        List<Wing> wingList = wingRepository.findAll();
        assertThat(wingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWings() throws Exception {
        // Initialize the database
        wingRepository.saveAndFlush(wing);

        // Get all the wingList
        restWingMockMvc.perform(get("/api/wings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wing.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())));
    }
    
    @Test
    @Transactional
    public void getWing() throws Exception {
        // Initialize the database
        wingRepository.saveAndFlush(wing);

        // Get the wing
        restWingMockMvc.perform(get("/api/wings/{id}", wing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wing.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingWing() throws Exception {
        // Get the wing
        restWingMockMvc.perform(get("/api/wings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWing() throws Exception {
        // Initialize the database
        wingRepository.saveAndFlush(wing);

        int databaseSizeBeforeUpdate = wingRepository.findAll().size();

        // Update the wing
        Wing updatedWing = wingRepository.findById(wing.getId()).get();
        // Disconnect from session so that the updates on updatedWing are not directly saved in db
        em.detach(updatedWing);
        updatedWing
            .identifier(UPDATED_IDENTIFIER);
        WingDTO wingDTO = wingMapper.toDto(updatedWing);

        restWingMockMvc.perform(put("/api/wings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wingDTO)))
            .andExpect(status().isOk());

        // Validate the Wing in the database
        List<Wing> wingList = wingRepository.findAll();
        assertThat(wingList).hasSize(databaseSizeBeforeUpdate);
        Wing testWing = wingList.get(wingList.size() - 1);
        assertThat(testWing.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingWing() throws Exception {
        int databaseSizeBeforeUpdate = wingRepository.findAll().size();

        // Create the Wing
        WingDTO wingDTO = wingMapper.toDto(wing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWingMockMvc.perform(put("/api/wings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wing in the database
        List<Wing> wingList = wingRepository.findAll();
        assertThat(wingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWing() throws Exception {
        // Initialize the database
        wingRepository.saveAndFlush(wing);

        int databaseSizeBeforeDelete = wingRepository.findAll().size();

        // Delete the wing
        restWingMockMvc.perform(delete("/api/wings/{id}", wing.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wing> wingList = wingRepository.findAll();
        assertThat(wingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
