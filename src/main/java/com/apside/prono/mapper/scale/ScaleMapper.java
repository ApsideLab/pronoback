package com.apside.prono.mapper.scale;

import com.apside.prono.model.ScaleEntity;
import com.apside.prono.modelapi.Scale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScaleMapper {

    ScaleMapper INSTANCE = Mappers.getMapper(ScaleMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    Scale mapScale(ScaleEntity actorEntity);
}
