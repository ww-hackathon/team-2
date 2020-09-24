package de.wwag.hackathon.team2.repository;

import de.wwag.hackathon.team2.domain.DailyReservation;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

/**
 * Spring Data  repository for the DailyReservation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyReservationRepository extends JpaRepository<DailyReservation, Long> {

    @Query("select dailyReservation from DailyReservation dailyReservation where dailyReservation.user.login = ?#{principal.username}")
    List<DailyReservation> findByUserIsCurrentUser();

    List<DailyReservation> findAllByDateIsBetween(LocalDate startDate, LocalDate endDate);
    // findAllByDateGreaterThanEqualStartDateAndDateLessThanEqualEndDate(LocalDate startDate, LocalDate endDate);
    //findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual

    @Query("select dailyReservation from DailyReservation dailyReservation where dailyReservation.date between :startDate and :endDate")
    List<DailyReservation> findAllDailyReservation(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate );

}
