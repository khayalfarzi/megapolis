package com.company.citylist.mapper

import com.company.citylist.dao.entity.CityEntity
import com.company.citylist.model.dto.CityDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

class CityMapperTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "mapToDto"() {
        given:
        def entity = random.nextObject(CityEntity)
        def response = new CityDto(entity.id, entity.name, entity.photo)

        when:
        def result = CityMapper.INSTANCE.mapToDto(entity)

        then:
        result == response
    }
}
