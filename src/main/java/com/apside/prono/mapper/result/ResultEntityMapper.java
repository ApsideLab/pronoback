package com.apside.prono.mapper.result;

import com.apside.prono.model.ResultEntity;
import com.apside.prono.modelapi.Result;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResultEntityMapper {

    ResultEntityMapper INSTANCE = Mappers.getMapper(ResultEntityMapper.class);

    @Mapping(target = "id",  source = "id")
    @Mapping(target = "result", source = "result")

    ResultEntity mapResultEntity(Result result);
}
