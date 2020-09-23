package de.wwag.hackathon.team2.service;

import de.wwag.hackathon.team2.domain.Deskgroup;
import de.wwag.hackathon.team2.repository.DeskgroupRepository;
import de.wwag.hackathon.team2.service.dto.DeskgroupDTO;
import de.wwag.hackathon.team2.service.mapper.DeskgroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Deskgroup}.
 */
@Service
@Transactional
public class DeskgroupService {

    private final Logger log = LoggerFactory.getLogger(DeskgroupService.class);

    private final DeskgroupRepository deskgroupRepository;

    private final DeskgroupMapper deskgroupMapper;

    public DeskgroupService(DeskgroupRepository deskgroupRepository, DeskgroupMapper deskgroupMapper) {
        this.deskgroupRepository = deskgroupRepository;
        this.deskgroupMapper = deskgroupMapper;
    }

    /**
     * Save a deskgroup.
     *
     * @param deskgroupDTO the entity to save.
     * @return the persisted entity.
     */
    public DeskgroupDTO save(DeskgroupDTO deskgroupDTO) {
        log.debug("Request to save Deskgroup : {}", deskgroupDTO);
        Deskgroup deskgroup = deskgroupMapper.toEntity(deskgroupDTO);
        deskgroup = deskgroupRepository.save(deskgroup);
        return deskgroupMapper.toDto(deskgroup);
    }

    /**
     * Get all the deskgroups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DeskgroupDTO> findAll() {
        log.debug("Request to get all Deskgroups");
        return deskgroupRepository.findAll().stream()
            .map(deskgroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one deskgroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeskgroupDTO> findOne(Long id) {
        log.debug("Request to get Deskgroup : {}", id);
        return deskgroupRepository.findById(id)
            .map(deskgroupMapper::toDto);
    }

    /**
     * Delete the deskgroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Deskgroup : {}", id);
        deskgroupRepository.deleteById(id);
    }
}
