package com.apside.prono.mapper.type;

import com.apside.prono.model.TypeEntity;
import com.apside.prono.modelapi.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TypeMapper {

    TypeMapper INSTANCE = Mappers.getMapper(TypeMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    Type mapType(TypeEntity typeEntity);
}
