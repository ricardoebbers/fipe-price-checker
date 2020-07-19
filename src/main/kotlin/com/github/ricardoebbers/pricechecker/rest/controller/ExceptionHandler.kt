package com.github.ricardoebbers.pricechecker.rest.controller

import com.github.ricardoebbers.pricechecker.rest.dto.ErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.logging.Logger


@RestControllerAdvice
class ExceptionHandler {
    companion object {
        private val log = Logger.getLogger(ExceptionHandler::class.java.simpleName)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<ErrorDTO> {
        log.warning("W=message_not_readable, message=${ex.message}")
        return ResponseEntity
                .badRequest()
                .body(ErrorDTO(
                        message = ex.message?.split(":")?.first(),
                        status = HttpStatus.BAD_REQUEST
                ))
    }

}
