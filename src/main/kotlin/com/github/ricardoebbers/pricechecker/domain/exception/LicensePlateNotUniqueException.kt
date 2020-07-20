package com.github.ricardoebbers.pricechecker.domain.exception

import org.springframework.http.HttpStatus

class LicensePlateNotUniqueException(
        licensePlate: String
) : BusinessException(HttpStatus.BAD_REQUEST) {
    override val message = "Placa '$licensePlate' já existe no banco de dados."
}
