package de.wwag.hackathon.team2.repository;

import de.wwag.hackathon.team2.domain.Deskgroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Deskgroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeskgroupRepository extends JpaRepository<Deskgroup, Long> {

    List<Deskgroup> findAllByIdIn(List<Long> idList);

    @Query("select deskgroup from Deskgroup deskgroup where deskgroup.id not in (:idList)")
    List<Deskgroup> findAllByIdIsNot(@Param("idList") List<Long> idList);

}
