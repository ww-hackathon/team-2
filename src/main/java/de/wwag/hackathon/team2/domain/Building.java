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
    @Column(name = "identifier", nullable = false)
    private Integer identifier;

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
