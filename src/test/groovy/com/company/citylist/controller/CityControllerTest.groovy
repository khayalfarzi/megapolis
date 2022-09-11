package com.company.citylist.controller

import com.company.citylist.domain.dto.CityDto
import com.company.citylist.domain.model.City
import com.company.citylist.service.CityService
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class CityControllerTest extends Specification {

    private CityController controller
    private CityService cityService
    private MockMvc mockMvc
    private random = EnhancedRandomBuilder.aNewEnhancedRandom()
    def mapper = new ObjectMapper()

    def "setup"() {
        cityService = Mock()
        controller = new CityController(cityService)
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ErrorHandler()).build()
    }

    def "findAll"() {
        given:
        def endpoint = "/cities?size=10&page=1&sort=id"
        def cities = List.of(random.nextObject(City))
        def citiesResponse =
                List.of(new CityDto(cities.get(0).id, cities.get(0).name, cities.get(0).photo))
        def cityServiceResponse = new PageImpl(cities)
        def response = new PageImpl(citiesResponse)
        def page = PageRequest.of(1, 10, Sort.by("id"))

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andReturn()

        then:
        1 * cityService.findAll(page) >> cityServiceResponse

        result.response.status == HttpStatus.OK.value()
        result.response.contentAsString == mapper.writeValueAsString(response)
    }

    def "findByName"() {
        given:
        def endpoint = "/cities/name/London"
        def response = random.nextObject(CityDto)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andReturn()

        then:
        cityService.findByName("London") >> response

        result.response.status == HttpStatus.OK.value()
        result.response.contentAsString == mapper.writeValueAsString(response)
    }

    def "update"() {
        given:
        def endpoint = "/cities"
        def request = random.nextObject(CityDto)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.put(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn()

        then:
        cityService.update(request)

        result.response.status == HttpStatus.OK.value()
    }
}
