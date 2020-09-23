package de.wwag.hackathon.team2.repository;

import de.wwag.hackathon.team2.domain.Building;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Building entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
}
