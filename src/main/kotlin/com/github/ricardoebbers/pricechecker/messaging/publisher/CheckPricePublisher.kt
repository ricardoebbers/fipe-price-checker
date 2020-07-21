package com.github.ricardoebbers.pricechecker.messaging.publisher

import com.github.ricardoebbers.pricechecker.messaging.message.VehicleMessage
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.logging.Logger


@Component
class CheckPricePublisher(
        private val template: RabbitTemplate,
        @Value("\${config.mq.check-price.exchange}")
        private val exchange: String,
        @Value("\${config.mq.check-price.routingKey}")
        private val routingKey: String
) {
    companion object {
        private val log = Logger.getLogger(CheckPricePublisher::class.java.simpleName)
    }

    fun send(message: VehicleMessage) {
        template.convertAndSend(exchange, routingKey, message)
        log.info("I=sent_message, message=$message exchange=$exchange, routingKey=$routingKey")
    }
}
