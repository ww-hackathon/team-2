package de.wwag.hackathon.team2.service.mapper;


import de.wwag.hackathon.team2.domain.*;
import de.wwag.hackathon.team2.service.dto.DeskgroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deskgroup} and its DTO {@link DeskgroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeskgroupMapper extends EntityMapper<DeskgroupDTO, Deskgroup> {



    default Deskgroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        Deskgroup deskgroup = new Deskgroup();
        deskgroup.setId(id);
        return deskgroup;
    }
}
