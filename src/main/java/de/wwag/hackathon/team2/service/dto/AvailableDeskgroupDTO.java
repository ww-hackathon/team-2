package de.wwag.hackathon.team2.service.dto;

import de.wwag.hackathon.team2.service.mapper.BuildingMapper;
import de.wwag.hackathon.team2.service.mapper.DeskgroupMapper;
import de.wwag.hackathon.team2.service.mapper.FloorMapper;
import de.wwag.hackathon.team2.service.mapper.WingMapper;

public class AvailableDeskgroupDTO {

    private BuildingDTO buildingDTO;
    private FloorDTO floorDTO;
    private WingDTO wingDTO;
    private DeskgroupDTO deskgroupDTO;




    public AvailableDeskgroupDTO(BuildingDTO buildingDTO, FloorDTO floorDTO, WingDTO wingDTO, DeskgroupDTO deskgroupDTO) {
        this.buildingDTO = buildingDTO;
        this.floorDTO = floorDTO;
        this.wingDTO = wingDTO;
        this.deskgroupDTO = deskgroupDTO;
    }


    public BuildingDTO getBuildingDTO() {
        return buildingDTO;
    }

    public void setBuildingDTO(BuildingDTO buildingDTO) {
        this.buildingDTO = buildingDTO;
    }

    public FloorDTO getFloorDTO() {
        return floorDTO;
    }

    public void setFloorDTO(FloorDTO floorDTO) {
        this.floorDTO = floorDTO;
    }

    public WingDTO getWingDTO() {
        return wingDTO;
    }

    public void setWingDTO(WingDTO wingDTO) {
        this.wingDTO = wingDTO;
    }

    public DeskgroupDTO getDeskgroupDTO() {
        return deskgroupDTO;
    }

    public void setDeskgroupDTO(DeskgroupDTO deskgroupDTO) {
        this.deskgroupDTO = deskgroupDTO;
    }
}
