package com.apside.prono.mapper.histoCalculs;

import com.apside.prono.model.HistoCalculsEntity;
import com.apside.prono.modelapi.HistoCalculs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HistoCalculsMapper {

    HistoCalculsMapper INSTANCE = Mappers.getMapper(HistoCalculsMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "contest", source = "contest")
    @Mapping(target = "rankHistoCalculs", source = "rankHistoCalculs")
    @Mapping(target = "dateHourBegin", source = "dateHourBegin")
    @Mapping(target = "dateHourEnd", source = "dateHourEnd")
    @Mapping(target = "dateHourCalculs", source = "dateHourCalculs")
    HistoCalculs mapHistoCalculs(HistoCalculsEntity histoCalculs);
}
