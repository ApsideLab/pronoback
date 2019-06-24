package com.apside.prono.mapper.pronoPlayer;

import com.apside.prono.model.PronoPlayerEntity;
import com.apside.prono.modelapi.PronoPlayer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PronoPlayerMapper {

    PronoPlayerMapper INSTANCE = Mappers.getMapper(PronoPlayerMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "player", source = "player")
    @Mapping(target = "event", source = "event")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "actor", source = "actor")
    @Mapping(target = "pronoDetail", source = "pronoDetail")
    @Mapping(target = "date", source = "date")
    PronoPlayer mapPronoPlayer(PronoPlayerEntity pronoPlayer);
}
