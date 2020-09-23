package de.wwag.hackathon.team2.service.mapper;


import de.wwag.hackathon.team2.domain.*;
import de.wwag.hackathon.team2.service.dto.BuildingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Building} and its DTO {@link BuildingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BuildingMapper extends EntityMapper<BuildingDTO, Building> {


    @Mapping(target = "floors", ignore = true)
    @Mapping(target = "removeFloor", ignore = true)
    Building toEntity(BuildingDTO buildingDTO);

    default Building fromId(Long id) {
        if (id == null) {
            return null;
        }
        Building building = new Building();
        building.setId(id);
        return building;
    }
}
