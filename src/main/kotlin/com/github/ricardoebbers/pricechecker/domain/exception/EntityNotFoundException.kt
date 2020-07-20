package com.github.ricardoebbers.pricechecker.domain.exception

import org.springframework.http.HttpStatus

class EntityNotFoundException(
        entityName: String,
        entityId: Int
) : BusinessException(HttpStatus.NOT_FOUND) {
    override val message = "$entityName com id '$entityId' não encontrado."

}
