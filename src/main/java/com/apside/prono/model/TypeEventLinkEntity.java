package com.apside.prono.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "typeEventLink")
@Data
public class TypeEventLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private TypeEntity type;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private EvenementEntity event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    public EvenementEntity getEvent() {
        return event;
    }

    public void setEvent(EvenementEntity event) {
        this.event = event;
    }
}