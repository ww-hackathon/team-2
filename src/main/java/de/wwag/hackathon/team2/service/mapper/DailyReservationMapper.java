package de.wwag.hackathon.team2.service.mapper;


import de.wwag.hackathon.team2.domain.*;
import de.wwag.hackathon.team2.service.dto.DailyReservationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DailyReservation} and its DTO {@link DailyReservationDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeskgroupMapper.class, UserMapper.class})
public interface DailyReservationMapper extends EntityMapper<DailyReservationDTO, DailyReservation> {

    @Mapping(source = "deskgroup.id", target = "deskgroupId")
    @Mapping(source = "user.id", target = "userId")
    DailyReservationDTO toDto(DailyReservation dailyReservation);

    @Mapping(source = "deskgroupId", target = "deskgroup")
    @Mapping(source = "userId", target = "user")
    DailyReservation toEntity(DailyReservationDTO dailyReservationDTO);

    default DailyReservation fromId(Long id) {
        if (id == null) {
            return null;
        }
        DailyReservation dailyReservation = new DailyReservation();
        dailyReservation.setId(id);
        return dailyReservation;
    }
}
