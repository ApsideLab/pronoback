package com.apside.prono.modelapi;

import java.util.Date;

public class Scale {
    private Long id;
    private String label;
    private Integer ptsBonResultat;
    private Integer ptsNbButs;
    private Integer ptsPunchingball;
    private Integer ptsPatator;
    private Integer ptsVainqueurFinal;
    private Boolean isActive;
    private Long contestId;

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

    public Integer getPtsBonResultat() {
        return ptsBonResultat;
    }

    public void setPtsBonResultat(Integer ptsBonResultat) {
        this.ptsBonResultat = ptsBonResultat;
    }

    public Integer getPtsNbButs() {
        return ptsNbButs;
    }

    public void setPtsNbButs(Integer ptsNbButs) {
        this.ptsNbButs = ptsNbButs;
    }

    public Integer getPtsPunchingball() {
        return ptsPunchingball;
    }

    public void setPtsPunchingball(Integer ptsPunchingball) {
        this.ptsPunchingball = ptsPunchingball;
    }

    public Integer getPtsPatator() {
        return ptsPatator;
    }

    public void setPtsPatator(Integer ptsPatator) {
        this.ptsPatator = ptsPatator;
    }

    public Integer getPtsVainqueurFinal() {
        return ptsVainqueurFinal;
    }

    public void setPtsVainqueurFinal(Integer ptsVainqueurFinal) {
        this.ptsVainqueurFinal = ptsVainqueurFinal;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getContestId() {
        return contestId;
    }

    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }
}