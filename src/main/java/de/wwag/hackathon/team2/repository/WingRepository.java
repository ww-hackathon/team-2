package de.wwag.hackathon.team2.repository;

import de.wwag.hackathon.team2.domain.Wing;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Wing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WingRepository extends JpaRepository<Wing, Long> {
}
