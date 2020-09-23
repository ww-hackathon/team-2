package de.wwag.hackathon.team2.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import de.wwag.hackathon.team2.domain.enumeration.FloorIdentifier;

/**
 * A DTO for the {@link de.wwag.hackathon.team2.domain.Floor} entity.
 */
public class FloorDTO implements Serializable {
    
    private Long id;

    @NotNull
    private FloorIdentifier identifier;


    private Long buildingId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FloorIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(FloorIdentifier identifier) {
        this.identifier = identifier;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FloorDTO)) {
            return false;
        }

        return id != null && id.equals(((FloorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FloorDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", buildingId=" + getBuildingId() +
            "}";
    }
}
