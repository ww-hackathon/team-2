package de.wwag.hackathon.team2.repository;

import de.wwag.hackathon.team2.domain.Floor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Floor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
}
