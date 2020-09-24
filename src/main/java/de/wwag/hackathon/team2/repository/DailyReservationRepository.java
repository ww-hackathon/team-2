package de.wwag.hackathon.team2.repository;

import de.wwag.hackathon.team2.domain.DailyReservation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the DailyReservation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyReservationRepository extends JpaRepository<DailyReservation, Long> {

    @Query("select dailyReservation from DailyReservation dailyReservation where dailyReservation.user.login = ?#{principal.username}")
    List<DailyReservation> findByUserIsCurrentUser();

}
