package com.company.citylist.domain.mapper


import com.company.citylist.domain.model.City
import com.company.citylist.domain.dto.CityDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

class CityMapperTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "mapToDto"() {
        given:
        def entity = random.nextObject(City)
        def response = new CityDto(entity.id, entity.name, entity.photo)

        when:
        def result = CityMapper.INSTANCE.mapToDto(entity)

        then:
        result == response
    }
}
