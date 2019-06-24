package com.apside.prono.mapper.prono;

import com.apside.prono.model.PronoEntity;
import com.apside.prono.modelapi.Prono;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PronoMapper {

    PronoMapper INSTANCE = Mappers.getMapper(PronoMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "score", source = "score")

    Prono mapProno(PronoEntity prono);
}
