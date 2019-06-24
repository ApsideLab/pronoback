package com.apside.prono.modelapi;
import java.util.Date;

public class ResultEvent {
    private String id;
    private Evenement evenement;
    private Type type;
    private Actor actor;
    private Result resultDetail;
    private Date date;


    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Result getResultDetail() {
        return resultDetail;
    }

    public void setResultDetail(Result resultDetail) {
        this.resultDetail = resultDetail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}