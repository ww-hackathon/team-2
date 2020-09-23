package de.wwag.hackathon.team2.service;

import de.wwag.hackathon.team2.domain.Floor;
import de.wwag.hackathon.team2.repository.FloorRepository;
import de.wwag.hackathon.team2.service.dto.FloorDTO;
import de.wwag.hackathon.team2.service.mapper.FloorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Floor}.
 */
@Service
@Transactional
public class FloorService {

    private final Logger log = LoggerFactory.getLogger(FloorService.class);

    private final FloorRepository floorRepository;

    private final FloorMapper floorMapper;

    public FloorService(FloorRepository floorRepository, FloorMapper floorMapper) {
        this.floorRepository = floorRepository;
        this.floorMapper = floorMapper;
    }

    /**
     * Save a floor.
     *
     * @param floorDTO the entity to save.
     * @return the persisted entity.
     */
    public FloorDTO save(FloorDTO floorDTO) {
        log.debug("Request to save Floor : {}", floorDTO);
        Floor floor = floorMapper.toEntity(floorDTO);
        floor = floorRepository.save(floor);
        return floorMapper.toDto(floor);
    }

    /**
     * Get all the floors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FloorDTO> findAll() {
        log.debug("Request to get all Floors");
        return floorRepository.findAll().stream()
            .map(floorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one floor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FloorDTO> findOne(Long id) {
        log.debug("Request to get Floor : {}", id);
        return floorRepository.findById(id)
            .map(floorMapper::toDto);
    }

    /**
     * Delete the floor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Floor : {}", id);
        floorRepository.deleteById(id);
    }
}
