package com.apside.prono.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "actorLinkEvent")
@Data
public class ActorLinkEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private ActorEntity actor;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private EvenementEntity event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActorEntity getActor() {
        return actor;
    }

    public void setActor(ActorEntity actor) {
        this.actor = actor;
    }

    public EvenementEntity getEvent() {
        return event;
    }

    public void setEvent(EvenementEntity event) {
        this.event = event;
    }
}