package com.github.ricardoebbers.pricechecker.messaging.consumer

import com.github.ricardoebbers.pricechecker.messaging.message.VehicleMessage
import com.github.ricardoebbers.pricechecker.rest.client.FipeClient
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.logging.Logger


@Component
class CheckPriceConsumer(
        private val fipeClient: FipeClient
) {
    companion object {
        private val log = Logger.getLogger(CheckPriceConsumer::class.java.simpleName)
    }

    @RabbitListener(queues = ["\${config.mq.check-price.queue}"])
    fun onMessage(message: VehicleMessage) {
        log.info("I=received_message, message=$message")
        fipeClient.checkPrice(message)
    }
}
