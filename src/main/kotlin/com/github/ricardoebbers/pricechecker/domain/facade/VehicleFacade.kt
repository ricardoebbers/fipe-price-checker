package com.github.ricardoebbers.pricechecker.domain.facade

import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import com.github.ricardoebbers.pricechecker.domain.entity.VehicleModel
import com.github.ricardoebbers.pricechecker.rest.command.CreateVehicleCommand

interface VehicleFacade {
    fun create(command: CreateVehicleCommand): Vehicle
    fun listVehicles(): List<Vehicle>
    fun listModels(): List<VehicleModel>
}
