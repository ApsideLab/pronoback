package com.apside.prono.modelapi;
import java.util.Date;

public class HistoRanking {
    private String id;
    private Player player;
    private Contest contest;
    private String rank;
    private Date date;
    private String position;
    private String points;
    private String bonus;
    private String goodProno;
    private String differencePointsRugby;
    private String nbTryOkRugby;
    private String scoreOkRugby;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getGoodProno() {
        return goodProno;
    }

    public void setGoodProno(String goodProno) {
        this.goodProno = goodProno;
    }

    public String getDifferencePointsRugby() {
        return differencePointsRugby;
    }

    public void setDifferencePointsRugby(String differencePointsRugby) {
        this.differencePointsRugby = differencePointsRugby;
    }

    public String getNbTryOkRugby() {
        return nbTryOkRugby;
    }

    public void setNbTryOkRugby(String nbTryOkRugby) {
        this.nbTryOkRugby = nbTryOkRugby;
    }

    public String getScoreOkRugby() {
        return scoreOkRugby;
    }

    public void setScoreOkRugby(String scoreOkRugby) {
        this.scoreOkRugby = scoreOkRugby;
    }
}