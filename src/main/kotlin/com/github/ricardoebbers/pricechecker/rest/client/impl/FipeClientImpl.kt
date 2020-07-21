package com.github.ricardoebbers.pricechecker.rest.client.impl

import com.github.ricardoebbers.pricechecker.domain.exception.VehiclePriceNotFound
import com.github.ricardoebbers.pricechecker.messaging.message.VehicleMessage
import com.github.ricardoebbers.pricechecker.messaging.publisher.CreateVehiclePublisher
import com.github.ricardoebbers.pricechecker.rest.client.FipeClient
import com.github.ricardoebbers.pricechecker.rest.client.dto.FipePriceDTO
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.util.logging.Logger

@Component
class FipeClientImpl(
        private val publisher: CreateVehiclePublisher,
        private val restTemplate: RestTemplate
) : FipeClient {
    companion object {
        private val log = Logger.getLogger(FipeClientImpl::class.java.simpleName)
        private const val GET_FIPE_PRICE_URL_TEMPLATE = "http://fipeapi.appspot.com/api/1/carros/veiculo/%d/%d/%d-1.json"
    }

    override fun checkPrice(message: VehicleMessage) {
        log.info("I=got_message, message=$message")
        try {
            fetchFipePrice(message).price.let {
                log.info("got_price_for_message, message=$message, price=$it")
                message.fipePrice = it
            }
            publisher.send(message)
        } catch (ex: HttpServerErrorException.InternalServerError) {
            log.warning("I=vehicle_not_found, message=$message")
            throw VehiclePriceNotFound(message)
        }

    }

    private fun fetchFipePrice(message: VehicleMessage): FipePriceDTO {
        return buildUrl(message).let {
            log.info("I=fetching_fipe_price, url=$it")
            restTemplate.getForObject(it)
        }
    }

    private fun buildUrl(message: VehicleMessage): String = String
            .format(GET_FIPE_PRICE_URL_TEMPLATE,
                    message.brandFipeId,
                    message.modelFipeId, message.year)
}
