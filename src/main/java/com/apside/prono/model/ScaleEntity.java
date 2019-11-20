package com.apside.prono.model;


import com.apside.prono.modelapi.Contest;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "scale")
@Data
public class ScaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String label;
    @NotNull
    private long ptsBonResultat;
    @NotNull
    private long ptsNbButs;
    @NotNull
    private long ptsPunchingball;
    @NotNull
    private long ptsPatator;
    @NotNull
    private long ptsVainqueurFinal;
    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean isActive;
    @ManyToOne
    private ContestEntity contest;

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

    public long getPtsBonResultat() {
        return ptsBonResultat;
    }

    public void setPtsBonResultat(long ptsBonResultat) {
        this.ptsBonResultat = ptsBonResultat;
    }

    public long getPtsNbButs() {
        return ptsNbButs;
    }

    public void setPtsNbButs(long ptsNbButs) {
        this.ptsNbButs = ptsNbButs;
    }

    public long getPtsPunchingball() {
        return ptsPunchingball;
    }

    public void setPtsPunchingball(long ptsPunchingball) {
        this.ptsPunchingball = ptsPunchingball;
    }

    public long getPtsPatator() {
        return ptsPatator;
    }

    public void setPtsPatator(long ptsPatator) {
        this.ptsPatator = ptsPatator;
    }

    public long getPtsVainqueurFinal() {
        return ptsVainqueurFinal;
    }

    public void setPtsVainqueurFinal(long ptsVainqueurFinal) {
        this.ptsVainqueurFinal = ptsVainqueurFinal;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public ContestEntity getContest() {
        return contest;
    }

    public void setContest(ContestEntity contest) {
        this.contest = contest;
    }
}