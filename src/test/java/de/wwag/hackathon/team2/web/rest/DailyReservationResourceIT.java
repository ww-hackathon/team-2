package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.WwHackathonTeam2App;
import de.wwag.hackathon.team2.domain.DailyReservation;
import de.wwag.hackathon.team2.repository.DailyReservationRepository;
import de.wwag.hackathon.team2.service.DailyReservationService;
import de.wwag.hackathon.team2.service.dto.DailyReservationDTO;
import de.wwag.hackathon.team2.service.mapper.DailyReservationMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DailyReservationResource} REST controller.
 */
@SpringBootTest(classes = WwHackathonTeam2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class DailyReservationResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DailyReservationRepository dailyReservationRepository;

    @Autowired
    private DailyReservationMapper dailyReservationMapper;

    @Autowired
    private DailyReservationService dailyReservationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDailyReservationMockMvc;

    private DailyReservation dailyReservation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyReservation createEntity(EntityManager em) {
        DailyReservation dailyReservation = new DailyReservation()
            .date(DEFAULT_DATE);
        return dailyReservation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyReservation createUpdatedEntity(EntityManager em) {
        DailyReservation dailyReservation = new DailyReservation()
            .date(UPDATED_DATE);
        return dailyReservation;
    }

    @BeforeEach
    public void initTest() {
        dailyReservation = createEntity(em);
    }

    @Test
    @Transactional
    public void createDailyReservation() throws Exception {
        int databaseSizeBeforeCreate = dailyReservationRepository.findAll().size();
        // Create the DailyReservation
        DailyReservationDTO dailyReservationDTO = dailyReservationMapper.toDto(dailyReservation);
        restDailyReservationMockMvc.perform(post("/api/daily-reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyReservationDTO)))
            .andExpect(status().isCreated());

        // Validate the DailyReservation in the database
        List<DailyReservation> dailyReservationList = dailyReservationRepository.findAll();
        assertThat(dailyReservationList).hasSize(databaseSizeBeforeCreate + 1);
        DailyReservation testDailyReservation = dailyReservationList.get(dailyReservationList.size() - 1);
        assertThat(testDailyReservation.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createDailyReservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dailyReservationRepository.findAll().size();

        // Create the DailyReservation with an existing ID
        dailyReservation.setId(1L);
        DailyReservationDTO dailyReservationDTO = dailyReservationMapper.toDto(dailyReservation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyReservationMockMvc.perform(post("/api/daily-reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyReservationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DailyReservation in the database
        List<DailyReservation> dailyReservationList = dailyReservationRepository.findAll();
        assertThat(dailyReservationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReservationRepository.findAll().size();
        // set the field null
        dailyReservation.setDate(null);

        // Create the DailyReservation, which fails.
        DailyReservationDTO dailyReservationDTO = dailyReservationMapper.toDto(dailyReservation);


        restDailyReservationMockMvc.perform(post("/api/daily-reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyReservationDTO)))
            .andExpect(status().isBadRequest());

        List<DailyReservation> dailyReservationList = dailyReservationRepository.findAll();
        assertThat(dailyReservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDailyReservations() throws Exception {
        // Initialize the database
        dailyReservationRepository.saveAndFlush(dailyReservation);

        // Get all the dailyReservationList
        restDailyReservationMockMvc.perform(get("/api/daily-reservations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyReservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDailyReservation() throws Exception {
        // Initialize the database
        dailyReservationRepository.saveAndFlush(dailyReservation);

        // Get the dailyReservation
        restDailyReservationMockMvc.perform(get("/api/daily-reservations/{id}", dailyReservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dailyReservation.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDailyReservation() throws Exception {
        // Get the dailyReservation
        restDailyReservationMockMvc.perform(get("/api/daily-reservations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDailyReservation() throws Exception {
        // Initialize the database
        dailyReservationRepository.saveAndFlush(dailyReservation);

        int databaseSizeBeforeUpdate = dailyReservationRepository.findAll().size();

        // Update the dailyReservation
        DailyReservation updatedDailyReservation = dailyReservationRepository.findById(dailyReservation.getId()).get();
        // Disconnect from session so that the updates on updatedDailyReservation are not directly saved in db
        em.detach(updatedDailyReservation);
        updatedDailyReservation
            .date(UPDATED_DATE);
        DailyReservationDTO dailyReservationDTO = dailyReservationMapper.toDto(updatedDailyReservation);

        restDailyReservationMockMvc.perform(put("/api/daily-reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyReservationDTO)))
            .andExpect(status().isOk());

        // Validate the DailyReservation in the database
        List<DailyReservation> dailyReservationList = dailyReservationRepository.findAll();
        assertThat(dailyReservationList).hasSize(databaseSizeBeforeUpdate);
        DailyReservation testDailyReservation = dailyReservationList.get(dailyReservationList.size() - 1);
        assertThat(testDailyReservation.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDailyReservation() throws Exception {
        int databaseSizeBeforeUpdate = dailyReservationRepository.findAll().size();

        // Create the DailyReservation
        DailyReservationDTO dailyReservationDTO = dailyReservationMapper.toDto(dailyReservation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyReservationMockMvc.perform(put("/api/daily-reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyReservationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DailyReservation in the database
        List<DailyReservation> dailyReservationList = dailyReservationRepository.findAll();
        assertThat(dailyReservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDailyReservation() throws Exception {
        // Initialize the database
        dailyReservationRepository.saveAndFlush(dailyReservation);

        int databaseSizeBeforeDelete = dailyReservationRepository.findAll().size();

        // Delete the dailyReservation
        restDailyReservationMockMvc.perform(delete("/api/daily-reservations/{id}", dailyReservation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DailyReservation> dailyReservationList = dailyReservationRepository.findAll();
        assertThat(dailyReservationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
