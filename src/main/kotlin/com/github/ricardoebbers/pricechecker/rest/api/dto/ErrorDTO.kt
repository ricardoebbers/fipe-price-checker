package com.github.ricardoebbers.pricechecker.rest.api.dto

import org.springframework.http.HttpStatus

data class ErrorDTO(
        val message: String?,
        val status: HttpStatus?
)
