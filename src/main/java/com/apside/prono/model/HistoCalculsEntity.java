package com.apside.prono.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "histoCalculs")
@Data
public class HistoCalculsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private ContestEntity contest = new ContestEntity();

    @NotNull
    @Column(name = "rankHistoCalculs", nullable = false)
    private Long rankHistoCalculs;
    @NotNull
    @Column(name = "DateHourBegin", nullable = false)
    private Date DateHourBegin;
    @NotNull
    @Column(name = "DateHourEnd", nullable = false)
    private Date DateHourEnd;
    @NotNull
    @Column(name = "DateHourCalculs", nullable = false)
    private Date DateHourCalculs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRankHistoCalculs() {
        return rankHistoCalculs;
    }

    public void setRankHistoCalculs(Long rankHistoCalculs) {
        this.rankHistoCalculs = rankHistoCalculs;
    }

    public Date getDateHourBegin() {
        return DateHourBegin;
    }

    public void setDateHourBegin(Date dateHourBegin) {
        DateHourBegin = dateHourBegin;
    }

    public Date getDateHourEnd() {
        return DateHourEnd;
    }

    public void setDateHourEnd(Date dateHourEnd) {
        DateHourEnd = dateHourEnd;
    }

    public Date getDateHourCalculs() {
        return DateHourCalculs;
    }

    public void setDateHourCalculs(Date dateHourCalculs) {
        DateHourCalculs = dateHourCalculs;
    }

    public ContestEntity getContest() {
        return contest;
    }

    public void setContest(ContestEntity contest) {
        this.contest = contest;
    }
}