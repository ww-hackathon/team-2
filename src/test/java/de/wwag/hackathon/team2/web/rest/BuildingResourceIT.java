package de.wwag.hackathon.team2.web.rest;

import de.wwag.hackathon.team2.WwHackathonTeam2App;
import de.wwag.hackathon.team2.domain.Building;
import de.wwag.hackathon.team2.repository.BuildingRepository;
import de.wwag.hackathon.team2.service.BuildingService;
import de.wwag.hackathon.team2.service.dto.BuildingDTO;
import de.wwag.hackathon.team2.service.mapper.BuildingMapper;

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
 * Integration tests for the {@link BuildingResource} REST controller.
 */
@SpringBootTest(classes = WwHackathonTeam2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class BuildingResourceIT {

    private static final Integer DEFAULT_IDENTIFIER = 1;
    private static final Integer UPDATED_IDENTIFIER = 2;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuildingMockMvc;

    private Building building;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Building createEntity(EntityManager em) {
        Building building = new Building()
            .identifier(DEFAULT_IDENTIFIER);
        return building;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Building createUpdatedEntity(EntityManager em) {
        Building building = new Building()
            .identifier(UPDATED_IDENTIFIER);
        return building;
    }

    @BeforeEach
    public void initTest() {
        building = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuilding() throws Exception {
        int databaseSizeBeforeCreate = buildingRepository.findAll().size();
        // Create the Building
        BuildingDTO buildingDTO = buildingMapper.toDto(building);
        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isCreated());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeCreate + 1);
        Building testBuilding = buildingList.get(buildingList.size() - 1);
        assertThat(testBuilding.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createBuildingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buildingRepository.findAll().size();

        // Create the Building with an existing ID
        building.setId(1L);
        BuildingDTO buildingDTO = buildingMapper.toDto(building);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = buildingRepository.findAll().size();
        // set the field null
        building.setIdentifier(null);

        // Create the Building, which fails.
        BuildingDTO buildingDTO = buildingMapper.toDto(building);


        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isBadRequest());

        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBuildings() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList
        restBuildingMockMvc.perform(get("/api/buildings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(building.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)));
    }
    
    @Test
    @Transactional
    public void getBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", building.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(building.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER));
    }
    @Test
    @Transactional
    public void getNonExistingBuilding() throws Exception {
        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        int databaseSizeBeforeUpdate = buildingRepository.findAll().size();

        // Update the building
        Building updatedBuilding = buildingRepository.findById(building.getId()).get();
        // Disconnect from session so that the updates on updatedBuilding are not directly saved in db
        em.detach(updatedBuilding);
        updatedBuilding
            .identifier(UPDATED_IDENTIFIER);
        BuildingDTO buildingDTO = buildingMapper.toDto(updatedBuilding);

        restBuildingMockMvc.perform(put("/api/buildings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isOk());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeUpdate);
        Building testBuilding = buildingList.get(buildingList.size() - 1);
        assertThat(testBuilding.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingBuilding() throws Exception {
        int databaseSizeBeforeUpdate = buildingRepository.findAll().size();

        // Create the Building
        BuildingDTO buildingDTO = buildingMapper.toDto(building);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuildingMockMvc.perform(put("/api/buildings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        int databaseSizeBeforeDelete = buildingRepository.findAll().size();

        // Delete the building
        restBuildingMockMvc.perform(delete("/api/buildings/{id}", building.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
