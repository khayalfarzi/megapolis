package com.company.citylist.service

import com.company.citylist.dao.entity.CityEntity
import com.company.citylist.dao.repository.CityRepository
import com.company.citylist.mapper.CityMapper
import com.company.citylist.model.dto.CityDto
import com.company.citylist.model.exception.NotFoundException
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.mockito.Mockito
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import spock.lang.Specification

class CityServiceTest extends Specification {

    private CityRepository repository
    private CityService cityService
    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "setup"() {
        repository = Mock()
        cityService = new CityService(repository)
    }

    def "findAll success"() {
        given:
        def entities = List.of(random.nextObject(CityEntity))
        def pageRequest = PageRequest.of(1, 10)
        def pageable = new PageImpl<>(entities, pageRequest, entities.size())
        def response = List.of(new CityDto(entities[0].id, entities[0].name, entities[0].photo))
        def page = PageRequest.of(10, 1, Sort.by("id").ascending())

        when:
        def result = cityService.findAll(page)

        then:
        repository.findAll(page) >> pageable

        result == response
    }

    def "findByName success"() {
        given:
        def name = "London"
        def entity = Optional.of(random.nextObject(CityEntity))
        def response = new CityDto(entity.get().getId(), entity.get().getName(), entity.get().getPhoto())

        when:
        def result = cityService.findByName(name)

        then:
        repository.findByName(name) >> entity

        response == result
    }

    def "findByName when name not found"() {
        given:
        def name = "London"
        def entity = Optional.empty()

        when:
        def result = cityService.findByName(name)

        then:
        repository.findByName(name) >> entity

        thrown(NotFoundException)
    }

    def "update success"() {
        given:
        def request = random.nextObject(CityDto)
        def entity = Optional.of(random.nextObject(CityEntity))
        def updatedCity = entity.get()
        updatedCity.name = request.name
        updatedCity.photo = request.photo

        when:
        cityService.update(request)

        then:
        repository.findById(request.id) >> entity
        repository.save(updatedCity) >> updatedCity
    }

    def "update when id not founded"() {
        given:
        def request = random.nextObject(CityDto)
        def entity = Optional.empty()

        when:
        cityService.update(request)

        then:
        repository.findById(request.id) >> entity

        thrown(NotFoundException)
    }
}
