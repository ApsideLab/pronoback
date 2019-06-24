package com.apside.prono.mapper.scale;

import com.apside.prono.model.ScaleEntity;
import com.apside.prono.modelapi.Scale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScaleEntityMapper {

    ScaleEntityMapper INSTANCE = Mappers.getMapper(ScaleEntityMapper.class);

    @Mapping(target = "id",  source = "id")
    @Mapping(target = "label", source = "label")
    ScaleEntity mapScaleEntity(Scale actor);
}
