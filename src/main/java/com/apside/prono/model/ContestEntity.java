package com.apside.prono.model;

import com.apside.prono.modelapi.Scale;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "contest")
public class ContestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "endDate", nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "contest")
    private Set<ScaleEntity> scales;

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() { return startDate; }

    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }

    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Set<ScaleEntity> getScales() {
        return scales;
    }

    public void setScales(Set<ScaleEntity> scales) {
        this.scales = scales;
    }

}