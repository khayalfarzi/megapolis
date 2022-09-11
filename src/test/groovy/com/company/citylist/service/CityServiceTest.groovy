package com.company.citylist.service

import com.company.citylist.domain.model.City
import com.company.citylist.repository.CityRepository
import com.company.citylist.domain.dto.CityDto
import com.company.citylist.exception.NotFoundException
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
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
        def entities = List.of(random.nextObject(City))
        def pageRequest = PageRequest.of(1, 10)
        def response = new PageImpl<>(entities, pageRequest, entities.size())
        def page = PageRequest.of(10, 1, Sort.by("id").ascending())

        when:
        def result = cityService.findAll(page)

        then:
        repository.findAll(page) >> response

        result == response
    }

    def "findByName success"() {
        given:
        def name = "London"
        def entity = Optional.of(random.nextObject(City))
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
        def entity = Optional.of(random.nextObject(City))
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
