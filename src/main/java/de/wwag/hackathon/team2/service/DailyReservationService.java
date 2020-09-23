package de.wwag.hackathon.team2.service;

import de.wwag.hackathon.team2.domain.DailyReservation;
import de.wwag.hackathon.team2.repository.DailyReservationRepository;
import de.wwag.hackathon.team2.service.dto.DailyReservationDTO;
import de.wwag.hackathon.team2.service.mapper.DailyReservationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DailyReservation}.
 */
@Service
@Transactional
public class DailyReservationService {

    private final Logger log = LoggerFactory.getLogger(DailyReservationService.class);

    private final DailyReservationRepository dailyReservationRepository;

    private final DailyReservationMapper dailyReservationMapper;

    public DailyReservationService(DailyReservationRepository dailyReservationRepository, DailyReservationMapper dailyReservationMapper) {
        this.dailyReservationRepository = dailyReservationRepository;
        this.dailyReservationMapper = dailyReservationMapper;
    }

    /**
     * Save a dailyReservation.
     *
     * @param dailyReservationDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyReservationDTO save(DailyReservationDTO dailyReservationDTO) {
        log.debug("Request to save DailyReservation : {}", dailyReservationDTO);
        DailyReservation dailyReservation = dailyReservationMapper.toEntity(dailyReservationDTO);
        dailyReservation = dailyReservationRepository.save(dailyReservation);
        return dailyReservationMapper.toDto(dailyReservation);
    }

    /**
     * Get all the dailyReservations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DailyReservationDTO> findAll() {
        log.debug("Request to get all DailyReservations");
        return dailyReservationRepository.findAll().stream()
            .map(dailyReservationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dailyReservation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DailyReservationDTO> findOne(Long id) {
        log.debug("Request to get DailyReservation : {}", id);
        return dailyReservationRepository.findById(id)
            .map(dailyReservationMapper::toDto);
    }

    /**
     * Delete the dailyReservation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DailyReservation : {}", id);
        dailyReservationRepository.deleteById(id);
    }
}
