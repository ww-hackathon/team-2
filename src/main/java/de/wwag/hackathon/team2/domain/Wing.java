package de.wwag.hackathon.team2.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import de.wwag.hackathon.team2.domain.enumeration.WingIdentifier;

/**
 * A Wing.
 */
@Entity
@Table(name = "wing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Wing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "identifier", nullable = false)
    private WingIdentifier identifier;

    @OneToMany(mappedBy = "wing")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Deskgroup> deskgroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WingIdentifier getIdentifier() {
        return identifier;
    }

    public Wing identifier(WingIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(WingIdentifier identifier) {
        this.identifier = identifier;
    }

    public Set<Deskgroup> getDeskgroups() {
        return deskgroups;
    }

    public Wing deskgroups(Set<Deskgroup> deskgroups) {
        this.deskgroups = deskgroups;
        return this;
    }

    public Wing addDeskgroups(Deskgroup deskgroup) {
        this.deskgroups.add(deskgroup);
        deskgroup.setWing(this);
        return this;
    }

    public Wing removeDeskgroups(Deskgroup deskgroup) {
        this.deskgroups.remove(deskgroup);
        deskgroup.setWing(null);
        return this;
    }

    public void setDeskgroups(Set<Deskgroup> deskgroups) {
        this.deskgroups = deskgroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wing)) {
            return false;
        }
        return id != null && id.equals(((Wing) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wing{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            "}";
    }
}
