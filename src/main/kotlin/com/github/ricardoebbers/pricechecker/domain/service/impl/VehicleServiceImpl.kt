package com.github.ricardoebbers.pricechecker.domain.service.impl

import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import com.github.ricardoebbers.pricechecker.domain.exception.LicensePlateNotUniqueException
import com.github.ricardoebbers.pricechecker.domain.repository.VehicleRepository
import com.github.ricardoebbers.pricechecker.domain.service.VehicleService
import com.github.ricardoebbers.pricechecker.messaging.message.VehicleMessage
import com.github.ricardoebbers.pricechecker.messaging.publisher.CheckPricePublisher
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class VehicleServiceImpl(
        private val repository: VehicleRepository,
        private val publisher: CheckPricePublisher
) : VehicleService {

    companion object {
        private val log = Logger.getLogger(VehicleServiceImpl::class.java.simpleName)
    }

    override fun requestPrice(vehicle: Vehicle) {
        validateUniqueLicensePlate(vehicle.licensePlate)
        publisher.send(VehicleMessage.from(vehicle))
    }

    override fun findByLicensePlate(licensePlate: String): Vehicle? {
        return repository.findByLicensePlateIgnoreCase(licensePlate)
    }

    override fun saveVehicle(vehicle: Vehicle): Vehicle {
        return saveUniqueVehicle(vehicle)
    }

    override fun listAll(): List<Vehicle> {
        return repository.findAll()
    }

    private fun validateUniqueLicensePlate(licensePlate: String) {
        if (repository.findByLicensePlateIgnoreCase(licensePlate) != null) {
            throw LicensePlateNotUniqueException(licensePlate)
        }
    }

    private fun saveUniqueVehicle(vehicle: Vehicle): Vehicle {
        return try {
            repository.save(vehicle)
        } catch (cve: DataIntegrityViolationException) {
            log.warning("W=license_plate_not_unique, licensePlate=${vehicle.licensePlate}")
            throw LicensePlateNotUniqueException(vehicle.licensePlate)
        }
    }
}
