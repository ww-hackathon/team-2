package de.wwag.hackathon.team2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DailyReservation.
 */
@Entity
@Table(name = "daily_reservation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DailyReservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties(value = "dailyReservations", allowSetters = true)
    private Deskgroup deskgroup;

    @ManyToOne
    @JsonIgnoreProperties(value = "dailyReservations", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public DailyReservation date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Deskgroup getDeskgroup() {
        return deskgroup;
    }

    public DailyReservation deskgroup(Deskgroup deskgroup) {
        this.deskgroup = deskgroup;
        return this;
    }

    public void setDeskgroup(Deskgroup deskgroup) {
        this.deskgroup = deskgroup;
    }

    public User getUser() {
        return user;
    }

    public DailyReservation user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyReservation)) {
            return false;
        }
        return id != null && id.equals(((DailyReservation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyReservation{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
