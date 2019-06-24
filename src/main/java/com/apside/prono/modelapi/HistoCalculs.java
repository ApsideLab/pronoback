package com.apside.prono.modelapi;
import java.util.Date;

public class HistoCalculs {
    private String id;
    private Contest contest;
    private String rankHistoCalculs;
    private Date DateHourBegin;
    private Date DateHourEnd;
    private Date DateHourCalculs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public String getRankHistoCalculs() {
        return rankHistoCalculs;
    }

    public void setRankHistoCalculs(String rankHistoCalculs) {
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
}