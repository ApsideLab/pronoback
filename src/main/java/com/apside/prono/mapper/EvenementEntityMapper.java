package com.apside.prono.mapper;

import com.apside.prono.model.EvenementEntity;
import com.apside.prono.modelapi.Evenement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EvenementEntityMapper {

    EvenementEntityMapper INSTANCE = Mappers.getMapper(EvenementEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "libelle", source = "libelle")
    @Mapping(target = "dateEvenement", source = "dateEvenement")
    @Mapping(target = "dateOuverture", source = "dateOuverture")
    @Mapping(target = "dateFermeture", source = "dateFermeture")
    @Mapping(target = "coeff", source = "coeff")
    EvenementEntity mapEvenementEntity(Evenement evenement);
}
