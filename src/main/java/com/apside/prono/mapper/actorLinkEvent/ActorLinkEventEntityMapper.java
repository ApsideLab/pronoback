package com.apside.prono.mapper.actorLinkEvent;

import com.apside.prono.model.ActorLinkEventEntity;
import com.apside.prono.modelapi.ActorLinkEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActorLinkEventEntityMapper {

    ActorLinkEventEntityMapper INSTANCE = Mappers.getMapper(ActorLinkEventEntityMapper.class);

    @Mapping(target = "id",  source = "id")
    @Mapping(target = "actor", source = "actor")
    @Mapping(target = "event", source = "event")
    ActorLinkEventEntity mapActorLinkEventEntity(ActorLinkEvent actorLinkEvent);
}
