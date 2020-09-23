package de.wwag.hackathon.team2.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link de.wwag.hackathon.team2.domain.Deskgroup} entity.
 */
public class DeskgroupDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Min(value = 1)
    private Integer seats;

    @NotNull
    private Integer identifier;

    @NotNull
    private Integer availableSeats;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeskgroupDTO)) {
            return false;
        }

        return id != null && id.equals(((DeskgroupDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeskgroupDTO{" +
            "id=" + getId() +
            ", seats=" + getSeats() +
            ", identifier=" + getIdentifier() +
            ", availableSeats=" + getAvailableSeats() +
            "}";
    }
}
