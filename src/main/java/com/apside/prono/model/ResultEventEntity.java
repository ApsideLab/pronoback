package com.apside.prono.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "resultEvent")
@Data
public class ResultEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private EvenementEntity evenementEntity = new EvenementEntity();

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private TypeEntity type = new TypeEntity();

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private ActorEntity actor = new ActorEntity();

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private ResultEntity resultDetail;

    @NotNull
    @Column(name = "date", nullable = false)
    private Date date;

    public EvenementEntity getEvenement() {
        return evenementEntity;
    }

    public void setEvenement(EvenementEntity evenementEntity) {
        this.evenementEntity = evenementEntity;
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

    public ResultEntity getResultDetail() {
        return resultDetail;
    }

    public void setResultDetail(ResultEntity resultDetail) {
        this.resultDetail = resultDetail;
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