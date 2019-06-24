package com.apside.prono.mapper.actor;

import com.apside.prono.model.ActorEntity;
import com.apside.prono.modelapi.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActorEntityMapper {

    ActorEntityMapper INSTANCE = Mappers.getMapper(ActorEntityMapper.class);

    @Mapping(target = "id",  source = "id")
    @Mapping(target = "label", source = "label")
    ActorEntity mapActorEntity(Actor actor);
}
