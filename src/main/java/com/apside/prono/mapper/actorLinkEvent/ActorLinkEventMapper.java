package com.apside.prono.mapper.actorLinkEvent;

import com.apside.prono.model.ActorLinkEventEntity;
import com.apside.prono.modelapi.ActorLinkEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActorLinkEventMapper {

    ActorLinkEventMapper INSTANCE = Mappers.getMapper(ActorLinkEventMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "actor", source = "actor")
    @Mapping(target = "event", source = "event")
    ActorLinkEvent mapActorLinkEvent(ActorLinkEventEntity actorLinkEvent);
}
