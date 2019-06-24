package com.apside.prono.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "pronoPlayer")
@Data
public class PronoPlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private PlayerEntity player;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private EvenementEntity event;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private TypeEntity type;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private ActorEntity actor;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private PronoEntity pronoDetail;

    @NotNull
    @Column(name = "date", nullable = false)
    private Date date;

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public EvenementEntity getEvent() {
        return event;
    }

    public void setEvent(EvenementEntity event) {
        this.event = event;
    }

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    public ActorEntity getActor() {
        return actor;
    }

    public void setActor(ActorEntity actor) {
        this.actor = actor;
    }

    public PronoEntity getPronoDetail() {
        return pronoDetail;
    }

    public void setPronoDetail(PronoEntity pronoDetail) {
        this.pronoDetail = pronoDetail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}