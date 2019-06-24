package com.apside.prono.mapper.type;

import com.apside.prono.model.TypeEntity;
import com.apside.prono.modelapi.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TypeEntityMapper {

    TypeEntityMapper INSTANCE = Mappers.getMapper(TypeEntityMapper.class);

    @Mapping(target = "id",  source = "id")
    @Mapping(target = "label", source = "label")
    TypeEntity mapTypeEntity(Type type);
}
