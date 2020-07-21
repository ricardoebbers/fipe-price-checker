package com.github.ricardoebbers.pricechecker.messaging.consumer

import com.github.ricardoebbers.pricechecker.domain.facade.VehicleFacade
import com.github.ricardoebbers.pricechecker.messaging.message.VehicleMessage
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.logging.Logger


@Component
class CreateVehicleConsumer(
        private val facade: VehicleFacade
) {
    companion object {
        private val log = Logger.getLogger(CreateVehicleConsumer::class.java.simpleName)
    }

    @RabbitListener(queues = ["\${config.mq.create-vehicle.queue}"])
    fun onMessage(message: VehicleMessage) {
        log.info("I=received_message, message=$message")
        facade.createVehicle(message)
    }
}
