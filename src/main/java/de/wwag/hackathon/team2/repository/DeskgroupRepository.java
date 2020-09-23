package de.wwag.hackathon.team2.repository;

import de.wwag.hackathon.team2.domain.Deskgroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Deskgroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeskgroupRepository extends JpaRepository<Deskgroup, Long> {
}
