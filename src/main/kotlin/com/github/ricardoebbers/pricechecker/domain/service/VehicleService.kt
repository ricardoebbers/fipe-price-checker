package com.github.ricardoebbers.pricechecker.domain.service

import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle

interface VehicleService {
    fun create(vehicle: Vehicle): Vehicle
    fun listAll(): List<Vehicle>
}
