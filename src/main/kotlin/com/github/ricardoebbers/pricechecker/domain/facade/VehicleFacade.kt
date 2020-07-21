package com.github.ricardoebbers.pricechecker.domain.facade

import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import com.github.ricardoebbers.pricechecker.domain.entity.VehicleModel
import com.github.ricardoebbers.pricechecker.messaging.message.VehicleMessage
import com.github.ricardoebbers.pricechecker.rest.api.command.RequestVehiclePriceCommand

interface VehicleFacade {
    fun requestPrice(command: RequestVehiclePriceCommand)
    fun listVehicles(): List<Vehicle>
    fun listModels(): List<VehicleModel>
    fun createVehicle(message: VehicleMessage)
}
