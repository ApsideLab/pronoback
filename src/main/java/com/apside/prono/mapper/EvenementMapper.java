package com.apside.prono.mapper;

import com.apside.prono.model.EvenementEntity;
import com.apside.prono.modelapi.Evenement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EvenementMapper {

    EvenementMapper INSTANCE = Mappers.getMapper(EvenementMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    @Mapping(target = "dateEvenement", source = "dateEvenement")
    @Mapping(target = "dateOuverture", source = "dateOuverture")
    @Mapping(target = "dateFermeture", source = "dateFermeture")
    @Mapping(target = "coeff", source = "coeff")
    Evenement mapEvenement(EvenementEntity evenement);
}
