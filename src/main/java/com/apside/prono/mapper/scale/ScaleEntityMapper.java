package com.apside.prono.mapper.scale;

import com.apside.prono.model.ScaleEntity;
import com.apside.prono.modelapi.Scale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScaleEntityMapper {

    ScaleEntityMapper INSTANCE = Mappers.getMapper(ScaleEntityMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    @Mapping(target = "ptsBonResultat", source="ptsBonResultat")
    @Mapping(target = "ptsNbButs", source="ptsNbButs")
    @Mapping(target = "ptsPunchingball", source="ptsPunchingball")
    @Mapping(target = "ptsPatator", source="ptsPatator")
    @Mapping(target = "ptsVainqueurFinal", source="ptsVainqueurFinal")
    @Mapping(target = "active", source="active")
    @Mapping(target = "contest", ignore=true)
    ScaleEntity mapScaleEntity(Scale scale);
}
