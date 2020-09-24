package de.wwag.hackathon.team2.repository;

import de.wwag.hackathon.team2.domain.DeskgroupThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeskgroupThresholdRepository extends JpaRepository<DeskgroupThreshold, Long> {
}
