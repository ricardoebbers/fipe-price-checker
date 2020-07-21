package com.github.ricardoebbers.pricechecker.domain.service

import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle

interface VehicleService {
    fun requestPrice(vehicle: Vehicle)
    fun saveVehicle(vehicle: Vehicle): Vehicle
    fun listAll(): List<Vehicle>
}
