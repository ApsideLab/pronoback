package com.apside.prono.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private long ptsBonusEcartButs;
    @NotNull
    private long ptsBonusDeuxScoresExacts;
    @NotNull
    private long ptsBonusUnScoreExactResultatOK;
    @NotNull
    private long ptsBonusUnScoreExactResultatKO;
    @NotNull
    private Date dateDebutValidite;
    @NotNull
    private Date dateFinValidite;

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

    public long getPtsBonusEcartButs() {
        return ptsBonusEcartButs;
    }

    public void setPtsBonusEcartButs(long ptsBonusEcartButs) {
        this.ptsBonusEcartButs = ptsBonusEcartButs;
    }

    public long getPtsBonusDeuxScoresExacts() {
        return ptsBonusDeuxScoresExacts;
    }

    public void setPtsBonusDeuxScoresExacts(long ptsBonusDeuxScoresExacts) {
        this.ptsBonusDeuxScoresExacts = ptsBonusDeuxScoresExacts;
    }

    public long getPtsBonusUnScoreExactResultatOK() {
        return ptsBonusUnScoreExactResultatOK;
    }

    public void setPtsBonusUnScoreExactResultatOK(long ptsBonusUnScoreExactResultatOK) {
        this.ptsBonusUnScoreExactResultatOK = ptsBonusUnScoreExactResultatOK;
    }

    public long getPtsBonusUnScoreExactResultatKO() {
        return ptsBonusUnScoreExactResultatKO;
    }

    public void setPtsBonusUnScoreExactResultatKO(long ptsBonusUnScoreExactResultatKO) {
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