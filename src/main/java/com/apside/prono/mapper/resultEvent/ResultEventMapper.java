package com.apside.prono.mapper.resultEvent;

import com.apside.prono.model.ResultEventEntity;
import com.apside.prono.modelapi.ResultEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResultEventMapper {

    ResultEventMapper INSTANCE = Mappers.getMapper(ResultEventMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "evenement", source = "evenement")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "actor", source = "actor")
    @Mapping(target = "resultDetail", source = "resultDetail")
    @Mapping(target = "date", source = "date")
    ResultEvent mapResultEvent(ResultEventEntity pronoPlayer);
}
