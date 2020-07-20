package com.github.ricardoebbers.pricechecker.domain.service

import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import com.github.ricardoebbers.pricechecker.rest.command.CreateVehicleCommand

interface VehicleService {
    fun create(command: CreateVehicleCommand): Vehicle
}
