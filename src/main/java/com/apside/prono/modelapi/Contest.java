package com.apside.prono.modelapi;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

public class Contest {
    private Long id;
    private String label;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Set<Scale> scales;

    public LocalDateTime getStartDate() { return startDate; }

    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }

    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Scale> getScales() {
        return scales;
    }

    public void setScales(Set<Scale> scales) {
        this.scales = scales;
    }
}