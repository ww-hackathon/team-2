package de.wwag.hackathon.team2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import de.wwag.hackathon.team2.domain.enumeration.FloorIdentifier;

/**
 * A Floor.
 */
@Entity
@Table(name = "floor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Floor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "identifier", nullable = false)
    private FloorIdentifier identifier;

    @OneToMany(mappedBy = "floor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Wing> wings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "floors", allowSetters = true)
    private Building building;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FloorIdentifier getIdentifier() {
        return identifier;
    }

    public Floor identifier(FloorIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(FloorIdentifier identifier) {
        this.identifier = identifier;
    }

    public Set<Wing> getWings() {
        return wings;
    }

    public Floor wings(Set<Wing> wings) {
        this.wings = wings;
        return this;
    }

    public Floor addWing(Wing wing) {
        this.wings.add(wing);
        wing.setFloor(this);
        return this;
    }

    public Floor removeWing(Wing wing) {
        this.wings.remove(wing);
        wing.setFloor(null);
        return this;
    }

    public void setWings(Set<Wing> wings) {
        this.wings = wings;
    }

    public Building getBuilding() {
        return building;
    }

    public Floor building(Building building) {
        this.building = building;
        return this;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Floor)) {
            return false;
        }
        return id != null && id.equals(((Floor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Floor{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            "}";
    }
}
