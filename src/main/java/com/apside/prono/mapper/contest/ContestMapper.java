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
    Contest mapContest(ContestEntity contest);
}
