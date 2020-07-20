package com.github.ricardoebbers.pricechecker.domain.service.impl

import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import com.github.ricardoebbers.pricechecker.domain.repository.VehicleRepository
import com.github.ricardoebbers.pricechecker.domain.service.VehicleService
import com.github.ricardoebbers.pricechecker.rest.command.CreateVehicleCommand
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class VehicleServiceImpl(
        private val repository: VehicleRepository
) : VehicleService {

    companion object {
        private val log = Logger.getLogger(VehicleServiceImpl::class.java.simpleName)
    }

    override fun create(command: CreateVehicleCommand): Vehicle {
        log.info("I=creating_vehicle, command=$command")
        return command.toEntity()
    }
}
