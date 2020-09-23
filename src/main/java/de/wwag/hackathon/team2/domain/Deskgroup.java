package de.wwag.hackathon.team2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne
    @JsonIgnoreProperties(value = "deskgroups", allowSetters = true)
    private Wing wing;

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

    public Wing getWing() {
        return wing;
    }

    public Deskgroup wing(Wing wing) {
        this.wing = wing;
        return this;
    }

    public void setWing(Wing wing) {
        this.wing = wing;
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
            "}";
    }
}
