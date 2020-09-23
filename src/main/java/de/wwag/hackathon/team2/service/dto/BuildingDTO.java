package de.wwag.hackathon.team2.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link de.wwag.hackathon.team2.domain.Building} entity.
 */
public class BuildingDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 14)
    private Integer identifier;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildingDTO)) {
            return false;
        }

        return id != null && id.equals(((BuildingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildingDTO{" +
            "id=" + getId() +
            ", identifier=" + getIdentifier() +
            "}";
    }
}
