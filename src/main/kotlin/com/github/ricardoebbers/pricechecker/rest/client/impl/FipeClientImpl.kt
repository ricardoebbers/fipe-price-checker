package com.github.ricardoebbers.pricechecker.rest.client.impl

import com.github.ricardoebbers.pricechecker.messaging.message.VehicleMessage
import com.github.ricardoebbers.pricechecker.messaging.publisher.CreateVehiclePublisher
import com.github.ricardoebbers.pricechecker.rest.client.FipeClient
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.logging.Logger

@Component
class FipeClientImpl(
        private val publisher: CreateVehiclePublisher
) : FipeClient {
    companion object {
        private val log = Logger.getLogger(FipeClientImpl::class.java.simpleName)
    }

    override fun checkPrice(message: VehicleMessage) {
        log.info("I=got_message, message=$message")
        message.fipePrice = BigDecimal.TEN
        publisher.send(message)
    }
}
