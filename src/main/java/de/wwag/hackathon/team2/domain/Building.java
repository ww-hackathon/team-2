package de.wwag.hackathon.team2.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Building.
 */
@Entity
@Table(name = "building")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 14)
    @Column(name = "identifier", nullable = false, unique = true)
    private Integer identifier;

    @OneToMany(mappedBy = "building")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Floor> floors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public Building identifier(Integer identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public Set<Floor> getFloors() {
        return floors;
    }

    public Building floors(Set<Floor> floors) {
        this.floors = floors;
        return this;
    }

    public Building addFloors(Floor floor) {
        this.floors.add(floor);
        floor.setBuilding(this);
        return this;
    }

    public Building removeFloors(Floor floor) {
        this.floors.remove(floor);
        floor.setBuilding(null);
        return this;
    }

    public void setFloors(Set<Floor> floors) {
        this.floors = floors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Building)) {
            return false;
        }
        return id != null && id.equals(((Building) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Building{" +
            "id=" + getId() +
            ", identifier=" + getIdentifier() +
            "}";
    }
}
