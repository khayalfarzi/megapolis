package com.company.citylist.service;

import com.company.citylist.dao.entity.CityEntity;
import com.company.citylist.dao.repository.CityRepository;
import com.company.citylist.mapper.CityMapper;
import com.company.citylist.model.dto.CityDto;
import com.company.citylist.model.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private static final Logger log = LoggerFactory.getLogger(CityService.class);
    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public List<CityDto> findAll(Pageable paging) {
        log.info("ActionLog.findAll start.");

        Page<CityEntity> pagedResult = repository.findAll(paging);

        List<CityDto> cities = getPagingResult(pagedResult);

        log.info("ActionLog.findAll end.");

        return cities;
    }

    public CityDto findByName(String name) {
        log.info("ActionLog.findByName start. #name: {}", name);

        CityEntity entity = repository.findByName(name)
                .orElseThrow(() -> new NotFoundException("NAME_NOT_FOUNDED"));

        CityDto dto = CityMapper.INSTANCE.mapToDto(entity);

        log.info("ActionLog.findByName end. #name: {}", name);

        return dto;
    }

    public void update(CityDto city) {
        log.info("ActionLog.update start. #id: {}", city.getId());

        CityEntity entity = repository.findById(city.getId())
                .orElseThrow(() -> new NotFoundException("CITY_ID_NOT_FOUNDED"));

        entity.setName(city.getName());
        entity.setPhoto(city.getPhoto());

        repository.save(entity);

        log.info("ActionLog.update end. #id: {}", city.getId());
    }

    private List<CityDto> getPagingResult(Page<CityEntity> cityEntities) {

        return cityEntities.hasContent() ?
                cityEntities.getContent()
                        .stream()
                        .map(CityMapper.INSTANCE::mapToDto)
                        .collect(Collectors.toList())
                : new ArrayList<>();
    }
}
