package com.github.ricardoebbers.pricechecker.domain.exception

import com.github.ricardoebbers.pricechecker.messaging.message.VehicleMessage
import org.springframework.http.HttpStatus

class VehiclePriceNotFound(
        message: VehicleMessage
) : BusinessException(HttpStatus.NOT_FOUND) {
    override val message = "Veículo com modelo '${message.modelFipeId}', marca '${message.brandFipeId}' e ano '${message.year}' não foi encontrado no FipeAPI."
}
