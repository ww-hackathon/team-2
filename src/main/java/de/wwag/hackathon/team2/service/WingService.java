package de.wwag.hackathon.team2.service;

import de.wwag.hackathon.team2.domain.Wing;
import de.wwag.hackathon.team2.repository.WingRepository;
import de.wwag.hackathon.team2.service.dto.WingDTO;
import de.wwag.hackathon.team2.service.mapper.WingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Wing}.
 */
@Service
@Transactional
public class WingService {

    private final Logger log = LoggerFactory.getLogger(WingService.class);

    private final WingRepository wingRepository;

    private final WingMapper wingMapper;

    public WingService(WingRepository wingRepository, WingMapper wingMapper) {
        this.wingRepository = wingRepository;
        this.wingMapper = wingMapper;
    }

    /**
     * Save a wing.
     *
     * @param wingDTO the entity to save.
     * @return the persisted entity.
     */
    public WingDTO save(WingDTO wingDTO) {
        log.debug("Request to save Wing : {}", wingDTO);
        Wing wing = wingMapper.toEntity(wingDTO);
        wing = wingRepository.save(wing);
        return wingMapper.toDto(wing);
    }

    /**
     * Get all the wings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WingDTO> findAll() {
        log.debug("Request to get all Wings");
        return wingRepository.findAll().stream()
            .map(wingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one wing by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WingDTO> findOne(Long id) {
        log.debug("Request to get Wing : {}", id);
        return wingRepository.findById(id)
            .map(wingMapper::toDto);
    }

    /**
     * Delete the wing by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Wing : {}", id);
        wingRepository.deleteById(id);
    }
}
