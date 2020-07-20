package com.github.ricardoebbers.pricechecker.domain.exception

import org.springframework.http.HttpStatus

open class BusinessException(val statusCode: HttpStatus) : RuntimeException()
