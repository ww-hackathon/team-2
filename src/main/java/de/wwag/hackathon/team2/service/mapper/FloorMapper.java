package de.wwag.hackathon.team2.service.mapper;


import de.wwag.hackathon.team2.domain.*;
import de.wwag.hackathon.team2.service.dto.FloorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Floor} and its DTO {@link FloorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FloorMapper extends EntityMapper<FloorDTO, Floor> {


    @Mapping(target = "wings", ignore = true)
    @Mapping(target = "removeWings", ignore = true)
    Floor toEntity(FloorDTO floorDTO);

    default Floor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Floor floor = new Floor();
        floor.setId(id);
        return floor;
    }
}
