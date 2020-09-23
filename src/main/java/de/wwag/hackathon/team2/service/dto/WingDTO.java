package de.wwag.hackathon.team2.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import de.wwag.hackathon.team2.domain.enumeration.WingIdentifier;

/**
 * A DTO for the {@link de.wwag.hackathon.team2.domain.Wing} entity.
 */
public class WingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private WingIdentifier identifier;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WingIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(WingIdentifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WingDTO)) {
            return false;
        }

        return id != null && id.equals(((WingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WingDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            "}";
    }
}
