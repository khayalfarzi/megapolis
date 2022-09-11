package com.company.citylist.controller

import com.company.citylist.domain.dto.ExceptionResponse
import com.company.citylist.exception.NotFoundException
import org.springframework.http.HttpStatus
import spock.lang.Specification

class ErrorHandlerTest extends Specification {

    private ErrorHandler errorHandler

    def setup() {
        errorHandler = new ErrorHandler()
    }

    def "HandleUnexpectedError"() {
        given:
        def ex = new Exception()
        def response = new ExceptionResponse("unexpected error", HttpStatus.INTERNAL_SERVER_ERROR.name())

        when:
        def result = errorHandler.handleUnexpectedError(ex)

        then:
        result == response
    }

    def "HandleException NotFoundException"() {
        given:
        def ex = new NotFoundException("NOT_FOUND", "NOT_FOUND_CODE")
        def response = new ExceptionResponse(ex.getMessage(), ex.getCode())

        when:
        def result = errorHandler.handleException(ex)

        then:
        result == response
    }
}