package de.wwag.hackathon.team2.service.mapper;


import de.wwag.hackathon.team2.domain.*;
import de.wwag.hackathon.team2.service.dto.WingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wing} and its DTO {@link WingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WingMapper extends EntityMapper<WingDTO, Wing> {


    @Mapping(target = "deskgroups", ignore = true)
    @Mapping(target = "removeDeskgroups", ignore = true)
    Wing toEntity(WingDTO wingDTO);

    default Wing fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wing wing = new Wing();
        wing.setId(id);
        return wing;
    }
}
