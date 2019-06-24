package com.apside.prono.mapper.TypeEventLink;

import com.apside.prono.model.TypeEventLinkEntity;
import com.apside.prono.modelapi.TypeEventLink;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TypeEventLinkEntityMapper {

    TypeEventLinkEntityMapper INSTANCE = Mappers.getMapper(TypeEventLinkEntityMapper.class);

    @Mapping(target = "id",  source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "event", source = "event")
    TypeEventLinkEntity mapTypeEventLinkEntity(TypeEventLink typeEventLink);
}
