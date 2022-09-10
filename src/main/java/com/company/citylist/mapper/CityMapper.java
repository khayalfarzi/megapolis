package com.company.citylist.mapper;

import com.company.citylist.dao.entity.CityEntity;
import com.company.citylist.model.dto.CityDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class CityMapper {

    public static final CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    public abstract CityDto mapToDto(CityEntity entity);
}
