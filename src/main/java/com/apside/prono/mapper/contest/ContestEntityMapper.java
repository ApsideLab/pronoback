package com.apside.prono.mapper.contest;

import com.apside.prono.model.ContestEntity;
import com.apside.prono.modelapi.Contest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContestEntityMapper {

    ContestEntityMapper INSTANCE = Mappers.getMapper(ContestEntityMapper.class);

    @Mapping(target = "id",  ignore = true)
    @Mapping(target = "label", source = "label")
    @Mapping(target = "startDate", source="startDate")
    @Mapping(target = "endDate", source="endDate")
    ContestEntity mapContestEntity(Contest contest);
}
