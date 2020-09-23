package de.wwag.hackathon.team2.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Deskgroup.
 */
@Entity
@Table(name = "deskgroup")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Deskgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "seats", nullable = false)
    private Integer seats;

    @NotNull
    @Column(name = "identifier", nullable = false)
    private Integer identifier;

    @NotNull
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeats() {
        return seats;
    }

    public Deskgroup seats(Integer seats) {
        this.seats = seats;
        return this;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public Deskgroup identifier(Integer identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public Deskgroup availableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
        return this;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deskgroup)) {
            return false;
        }
        return id != null && id.equals(((Deskgroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deskgroup{" +
            "id=" + getId() +
            ", seats=" + getSeats() +
            ", identifier=" + getIdentifier() +
            ", availableSeats=" + getAvailableSeats() +
            "}";
    }
}
