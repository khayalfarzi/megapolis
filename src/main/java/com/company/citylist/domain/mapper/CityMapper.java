package com.company.citylist.domain.mapper;

import com.company.citylist.domain.model.City;
import com.company.citylist.domain.dto.CityDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class CityMapper {

    public static final CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    public abstract CityDto mapToDto(City entity);
}
