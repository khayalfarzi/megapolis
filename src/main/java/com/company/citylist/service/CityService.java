package com.company.citylist.service;

import com.company.citylist.domain.model.City;
import com.company.citylist.repository.CityRepository;
import com.company.citylist.domain.mapper.CityMapper;
import com.company.citylist.domain.dto.CityDto;
import com.company.citylist.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private static final Logger log = LoggerFactory.getLogger(CityService.class);
    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public Page<City> findAll(Pageable paging) {
        log.info("ActionLog.findAll start.");

        Page<City> cities = repository.findAll(paging);

        log.info("ActionLog.findAll end.");

        return cities;
    }

    public City findByName(String name) {
        log.info("ActionLog.findByName start. #name: {}", name);

        City entity = repository.findByName(name)
                .orElseThrow(() -> new NotFoundException("NAME_NOT_FOUNDED"));

        log.info("ActionLog.findByName end. #name: {}", name);

        return entity;
    }

    public void update(CityDto city) {
        log.info("ActionLog.update start. #id: {}", city.getId());

        City entity = repository.findById(city.getId())
                .orElseThrow(() -> new NotFoundException("CITY_ID_NOT_FOUNDED"));

        entity.setName(city.getName());
        entity.setPhoto(city.getPhoto());

        repository.save(entity);

        log.info("ActionLog.update end. #id: {}", city.getId());
    }
}
