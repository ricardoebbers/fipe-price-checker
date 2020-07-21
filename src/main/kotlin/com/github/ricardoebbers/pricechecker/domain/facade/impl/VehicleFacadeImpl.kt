package com.github.ricardoebbers.pricechecker.domain.facade.impl

import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import com.github.ricardoebbers.pricechecker.domain.entity.VehicleModel
import com.github.ricardoebbers.pricechecker.domain.facade.VehicleFacade
import com.github.ricardoebbers.pricechecker.domain.service.VehicleModelService
import com.github.ricardoebbers.pricechecker.domain.service.VehicleService
import com.github.ricardoebbers.pricechecker.messaging.message.VehicleMessage
import com.github.ricardoebbers.pricechecker.rest.api.command.RequestVehiclePriceCommand
import org.springframework.stereotype.Component

@Component
class VehicleFacadeImpl(
        private val vehicleService: VehicleService,
        private val vehicleModelService: VehicleModelService
) : VehicleFacade {
    override fun requestPrice(command: RequestVehiclePriceCommand) {
        val model = vehicleModelService.getModelByFipeId(command.modelId, command.brandId)
        val vehicle = command.toEntity().copy(model = model)
        return vehicleService.requestPrice(vehicle)
    }

    override fun listVehicles(): List<Vehicle> {
        return vehicleService.listAll()
    }

    override fun listModels(): List<VehicleModel> {
        return vehicleModelService.listModels()
    }

    override fun createVehicle(message: VehicleMessage) {
        val model = vehicleModelService.getModelByFipeId(message.modelFipeId, message.brandFipeId)
        val vehicle = message.toEntity().copy(model = model)
        vehicleService.saveVehicle(vehicle)
    }
}
