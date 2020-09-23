package de.wwag.hackathon.team2.service.mapper;


import de.wwag.hackathon.team2.domain.*;
import de.wwag.hackathon.team2.service.dto.DeskgroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deskgroup} and its DTO {@link DeskgroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {WingMapper.class})
public interface DeskgroupMapper extends EntityMapper<DeskgroupDTO, Deskgroup> {

    @Mapping(source = "wing.id", target = "wingId")
    DeskgroupDTO toDto(Deskgroup deskgroup);

    @Mapping(source = "wingId", target = "wing")
    Deskgroup toEntity(DeskgroupDTO deskgroupDTO);

    default Deskgroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        Deskgroup deskgroup = new Deskgroup();
        deskgroup.setId(id);
        return deskgroup;
    }
}
