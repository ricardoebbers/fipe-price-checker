package com.github.ricardoebbers.pricechecker.rest.client

import com.github.ricardoebbers.pricechecker.messaging.message.VehicleMessage

interface FipeClient {
    fun checkPrice(message: VehicleMessage)
}
