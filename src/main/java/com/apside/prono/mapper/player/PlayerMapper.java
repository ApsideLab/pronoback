package com.apside.prono.mapper.player;

import com.apside.prono.model.PlayerEntity;
import com.apside.prono.modelapi.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerMapper {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "mail", source = "mail")
    @Mapping(target = "subscribeDate", source = "subscribeDate")
    Player mapPlayer(PlayerEntity PlayerEntity);
}
