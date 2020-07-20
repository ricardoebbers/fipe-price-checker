package com.github.ricardoebbers.pricechecker.rest.controller

import com.github.ricardoebbers.pricechecker.rest.dto.ErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.logging.Logger
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class ExceptionHandler {
    companion object {
        private val log = Logger.getLogger(ExceptionHandler::class.java.simpleName)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMessageNotReadable(
            ex: HttpMessageNotReadableException
    ): ResponseEntity<ErrorDTO> {
        logException("message_not_readable", ex.message)
        return ResponseEntity
                .badRequest()
                .body(ErrorDTO(
                        message = ex.message?.split(":")?.first(),
                        status = HttpStatus.BAD_REQUEST
                ))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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
                        status = HttpStatus.BAD_REQUEST
                ))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(
            ex: MethodArgumentTypeMismatchException
    ): ResponseEntity<ErrorDTO> {
        logException("type_mismatch", ex.message)
        return ResponseEntity
                .badRequest()
                .body(ErrorDTO(
                        message = ex.message,
                        status = HttpStatus.BAD_REQUEST
                ))
    }

    private fun logException(warning: String, message: String?) {
        log.warning("W=$warning, message=$message")
    }

}
