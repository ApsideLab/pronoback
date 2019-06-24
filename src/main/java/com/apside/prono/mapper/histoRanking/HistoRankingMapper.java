package com.apside.prono.mapper.histoRanking;

import com.apside.prono.model.HistoRankingEntity;
import com.apside.prono.modelapi.HistoRanking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HistoRankingMapper {

    HistoRankingMapper INSTANCE = Mappers.getMapper(HistoRankingMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "player", source = "player")
    @Mapping(target = "contest", source = "contest")
    @Mapping(target = "rank", source = "rank")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "position", source = "position")
    @Mapping(target = "points", source = "points")
    @Mapping(target = "bonus", source = "bonus")
    @Mapping(target = "goodProno", source = "goodProno")
    @Mapping(target = "differencePointsRugby", source = "differencePointsRugby")
    @Mapping(target = "nbTryOkRugby", source = "nbTryOkRugby")
    @Mapping(target = "scoreOkRugby", source = "scoreOkRugby")
    
    HistoRanking mapHistoRanking(HistoRankingEntity histoRanking);
}
