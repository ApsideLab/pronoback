package com.apside.prono.mapper.contest;

import com.apside.prono.model.ContestEntity;
import com.apside.prono.modelapi.Contest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContestMapper {

    ContestMapper INSTANCE = Mappers.getMapper(ContestMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    @Mapping(target = "startDate", source="startDate")
    @Mapping(target = "endDate", source="endDate")
    Contest mapContest(ContestEntity contest);
}
