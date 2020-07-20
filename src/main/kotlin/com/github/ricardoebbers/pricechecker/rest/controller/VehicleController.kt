package com.github.ricardoebbers.pricechecker.rest.controller

import com.github.ricardoebbers.pricechecker.domain.service.VehicleService
import com.github.ricardoebbers.pricechecker.rest.command.CreateVehicleCommand
import com.github.ricardoebbers.pricechecker.rest.dto.VehicleDTO
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
@RequestMapping("/v1/veiculos")
@Tag(name = "Veículos")
class VehicleController(
        private val service: VehicleService
) {
    companion object {
        private val log = Logger.getLogger(this::class.java.simpleName)
    }

    @PostMapping
    fun createVehicle(@RequestBody command: CreateVehicleCommand ) : VehicleDTO {
        log.info("I=init, command=$command")
        return VehicleDTO.from(service.create(command)).also {
            log.info("I=finish, vehicle=$it")
        }
    }


}
