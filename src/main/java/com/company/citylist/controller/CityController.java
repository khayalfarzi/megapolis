package com.company.citylist.controller;

import com.company.citylist.domain.dto.CityDto;
import com.company.citylist.domain.mapper.CityMapper;
import com.company.citylist.service.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping
    public Page<CityDto> findAll(Pageable pageable) {
        return service.findAll(pageable).map(CityMapper.INSTANCE::mapToDto);
    }

    @GetMapping("/name/{name}")
    public CityDto findByName(@PathVariable String name) {
        return CityMapper.INSTANCE.mapToDto(service.findByName(name));
    }

    @PutMapping
    public void update(@RequestBody CityDto city) {
        service.update(city);
    }
}