package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.WwHackathonTeam2App;
import de.wwag.hackathon.team2.domain.Floor;
import de.wwag.hackathon.team2.repository.FloorRepository;
import de.wwag.hackathon.team2.service.FloorService;
import de.wwag.hackathon.team2.service.dto.FloorDTO;
import de.wwag.hackathon.team2.service.mapper.FloorMapper;

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

import de.wwag.hackathon.team2.domain.enumeration.FloorIdentifier;
/**
 * Integration tests for the {@link FloorResource} REST controller.
 */
@SpringBootTest(classes = WwHackathonTeam2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class FloorResourceIT {

    private static final FloorIdentifier DEFAULT_IDENTIFIER = FloorIdentifier.UG;
    private static final FloorIdentifier UPDATED_IDENTIFIER = FloorIdentifier.CG;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private FloorMapper floorMapper;

    @Autowired
    private FloorService floorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFloorMockMvc;

    private Floor floor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Floor createEntity(EntityManager em) {
        Floor floor = new Floor()
            .identifier(DEFAULT_IDENTIFIER);
        return floor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Floor createUpdatedEntity(EntityManager em) {
        Floor floor = new Floor()
            .identifier(UPDATED_IDENTIFIER);
        return floor;
    }

    @BeforeEach
    public void initTest() {
        floor = createEntity(em);
    }

    @Test
    @Transactional
    public void createFloor() throws Exception {
        int databaseSizeBeforeCreate = floorRepository.findAll().size();
        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);
        restFloorMockMvc.perform(post("/api/floors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isCreated());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeCreate + 1);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createFloorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = floorRepository.findAll().size();

        // Create the Floor with an existing ID
        floor.setId(1L);
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFloorMockMvc.perform(post("/api/floors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = floorRepository.findAll().size();
        // set the field null
        floor.setIdentifier(null);

        // Create the Floor, which fails.
        FloorDTO floorDTO = floorMapper.toDto(floor);


        restFloorMockMvc.perform(post("/api/floors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isBadRequest());

        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFloors() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        // Get all the floorList
        restFloorMockMvc.perform(get("/api/floors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(floor.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())));
    }
    
    @Test
    @Transactional
    public void getFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        // Get the floor
        restFloorMockMvc.perform(get("/api/floors/{id}", floor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(floor.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFloor() throws Exception {
        // Get the floor
        restFloorMockMvc.perform(get("/api/floors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Update the floor
        Floor updatedFloor = floorRepository.findById(floor.getId()).get();
        // Disconnect from session so that the updates on updatedFloor are not directly saved in db
        em.detach(updatedFloor);
        updatedFloor
            .identifier(UPDATED_IDENTIFIER);
        FloorDTO floorDTO = floorMapper.toDto(updatedFloor);

        restFloorMockMvc.perform(put("/api/floors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isOk());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFloorMockMvc.perform(put("/api/floors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        int databaseSizeBeforeDelete = floorRepository.findAll().size();

        // Delete the floor
        restFloorMockMvc.perform(delete("/api/floors/{id}", floor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
