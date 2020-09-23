package de.wwag.hackathon.team2.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link de.wwag.hackathon.team2.domain.DailyReservation} entity.
 */
public class DailyReservationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private LocalDate date;


    private Long deskgroupId;

    private Long userId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getDeskgroupId() {
        return deskgroupId;
    }

    public void setDeskgroupId(Long deskgroupId) {
        this.deskgroupId = deskgroupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyReservationDTO)) {
            return false;
        }

        return id != null && id.equals(((DailyReservationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyReservationDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", deskgroupId=" + getDeskgroupId() +
            ", userId=" + getUserId() +
            "}";
    }
}
