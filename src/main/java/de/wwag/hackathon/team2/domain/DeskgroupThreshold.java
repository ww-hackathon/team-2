package de.wwag.hackathon.team2.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "deskgroup_threshold")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeskgroupThreshold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "threshold")
    private double threshold;

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
