package com.github.ricardoebbers.pricechecker.rest.api.controller

import com.github.ricardoebbers.pricechecker.domain.exception.BusinessException
import com.github.ricardoebbers.pricechecker.rest.api.dto.ErrorDTO
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.logging.Logger
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {
        private val log = Logger.getLogger(ExceptionHandler::class.java.simpleName)
    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity
                .badRequest()
                .body(ErrorDTO(
                        message = ex.message?.split(":")?.first(),
                        status = BAD_REQUEST
                ))
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        log.warning("")
        return ResponseEntity
                .badRequest()
                .body(ErrorDTO(
                        message = ex.bindingResult.fieldErrors.joinToString(", ") { "${it.field} ${it.defaultMessage}" },
                        status = BAD_REQUEST
                ))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(BAD_REQUEST)
    fun handleMethodArgumentTypeMismatchException(
            ex: MethodArgumentTypeMismatchException
    ): ResponseEntity<ErrorDTO> {
        logException("type_mismatch", ex.message)
        return ResponseEntity
                .badRequest()
                .body(ErrorDTO(
                        message = ex.message,
                        status = BAD_REQUEST
                ))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(BAD_REQUEST)
    fun handleConstraintViolationException(
            ex: ConstraintViolationException
    ): ResponseEntity<ErrorDTO> {
        logException("constraint_violation", ex.message)
        return ResponseEntity
                .badRequest()
                .body(ErrorDTO(
                        message = ex.constraintViolations.joinToString(separator = ", ") {
                            "${it.invalidValue} ${it.message}"
                        },
                        status = BAD_REQUEST
                ))
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(
            ex: BusinessException
    ): ResponseEntity<ErrorDTO> {
        logException("business_exception", ex.message)
        return ResponseEntity
                .status(ex.statusCode)
                .body(ErrorDTO(
                        message = ex.message,
                        status = ex.statusCode
                ))
    }

    private fun logException(warning: String, message: String?) {
        log.warning("W=$warning, message=$message")
    }

}
