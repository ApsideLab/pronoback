package com.apside.prono.model;

import com.apside.prono.modelapi.Contest;
import com.apside.prono.modelapi.Player;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "histoRanking")
@Data
public class HistoRankingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private PlayerEntity player;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private ContestEntity contest;

    @NotNull
    @Column(name = "rank", nullable = false)
    private Long rank;

    @NotNull
    @Column(name = "date", nullable = false)
    private Date date;

    @NotNull
    @Column(name = "position", nullable = false)
    private Long position;

    @NotNull
    @Column(name = "points", nullable = false)
    private Long points;

    @NotNull
    @Column(name = "bonus", nullable = false)
    private Long bonus;

    @NotNull
    @Column(name = "goodProno", nullable = false)
    private Long goodProno;

    @NotNull
    @Column(name = "differencePointsRugby", nullable = false)
    private Long differencePointsRugby;

    @NotNull
    @Column(name = "nbTryOkRugby", nullable = false)
    private Long nbTryOkRugby;

    @NotNull
    @Column(name = "scoreOkRugby", nullable = false)
    private Long scoreOkRugby;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public ContestEntity getContest() {
        return contest;
    }

    public void setContest(ContestEntity contest) {
        this.contest = contest;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Long getBonus() {
        return bonus;
    }

    public void setBonus(Long bonus) {
        this.bonus = bonus;
    }

    public Long getGoodProno() {
        return goodProno;
    }

    public void setGoodProno(Long goodProno) {
        this.goodProno = goodProno;
    }

    public Long getDifferencePointsRugby() {
        return differencePointsRugby;
    }

    public void setDifferencePointsRugby(Long differencePointsRugby) {
        this.differencePointsRugby = differencePointsRugby;
    }

    public Long getNbTryOkRugby() {
        return nbTryOkRugby;
    }

    public void setNbTryOkRugby(Long nbTryOkRugby) {
        this.nbTryOkRugby = nbTryOkRugby;
    }

    public Long getScoreOkRugby() {
        return scoreOkRugby;
    }

    public void setScoreOkRugby(Long scoreOkRugby) {
        this.scoreOkRugby = scoreOkRugby;
    }
}