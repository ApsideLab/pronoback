package com.apside.prono.modelapi;

import com.apside.prono.model.*;

import java.util.Date;

public class PronoPlayer {
    private String id;
    private Player player;
    private Evenement event;
    private Type type;
    private Actor actor;
    private Prono pronoDetail;
    private Date date;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Evenement getEvent() {
        return event;
    }

    public void setEvent(Evenement event) {
        this.event = event;
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

    public Prono getPronoDetail() {
        return pronoDetail;
    }

    public void setPronoDetail(Prono idPronoDetail) {
        this.pronoDetail = idPronoDetail;
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