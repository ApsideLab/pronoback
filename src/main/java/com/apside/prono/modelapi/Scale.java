package com.apside.prono.modelapi;

import java.util.Date;

public class Scale {
    private String id;
    private String label;
    private Date dateDebutValidite;
    private String ptsBonResultat;
    private String ptsBonusEcartButs;
    private String ptsBonusDeuxScoresExacts;
    private String ptsBonusUnScoreExactResultatOK;
    private String ptsBonusUnScoreExactResultatKO;
    private Date dateFinValidite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPtsBonResultat() {
        return ptsBonResultat;
    }

    public void setPtsBonResultat(String ptsBonResultat) {
        this.ptsBonResultat = ptsBonResultat;
    }

    public String getPtsBonusEcartButs() {
        return ptsBonusEcartButs;
    }

    public void setPtsBonusEcartButs(String ptsBonusEcartButs) {
        this.ptsBonusEcartButs = ptsBonusEcartButs;
    }

    public String getPtsBonusDeuxScoresExacts() {
        return ptsBonusDeuxScoresExacts;
    }

    public void setPtsBonusDeuxScoresExacts(String ptsBonusDeuxScoresExacts) {
        this.ptsBonusDeuxScoresExacts = ptsBonusDeuxScoresExacts;
    }

    public String getPtsBonusUnScoreExactResultatOK() {
        return ptsBonusUnScoreExactResultatOK;
    }

    public void setPtsBonusUnScoreExactResultatOK(String ptsBonusUnScoreExactResultatOK) {
        this.ptsBonusUnScoreExactResultatOK = ptsBonusUnScoreExactResultatOK;
    }

    public String getPtsBonusUnScoreExactResultatKO() {
        return ptsBonusUnScoreExactResultatKO;
    }

    public void setPtsBonusUnScoreExactResultatKO(String ptsBonusUnScoreExactResultatKO) {
        this.ptsBonusUnScoreExactResultatKO = ptsBonusUnScoreExactResultatKO;
    }

    public Date getDateDebutValidite() {
        return dateDebutValidite;
    }

    public void setDateDebutValidite(Date dateDebutValidite) {
        this.dateDebutValidite = dateDebutValidite;
    }

    public Date getDateFinValidite() {
        return dateFinValidite;
    }

    public void setDateFinValidite(Date dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
    }
}