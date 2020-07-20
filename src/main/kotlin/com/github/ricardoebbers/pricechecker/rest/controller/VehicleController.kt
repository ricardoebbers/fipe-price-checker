package com.github.ricardoebbers.pricechecker.rest.controller

import com.github.ricardoebbers.pricechecker.domain.facade.VehicleFacade
import com.github.ricardoebbers.pricechecker.rest.command.CreateVehicleCommand
import com.github.ricardoebbers.pricechecker.rest.dto.VehicleDTO
import com.github.ricardoebbers.pricechecker.rest.dto.VehicleModelDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger
import javax.validation.Valid

@RestController
@RequestMapping("/v1/veiculos")
@Tag(name = "Veículos")
class VehicleController(
        private val facade: VehicleFacade
) {
    companion object {
        private val log = Logger.getLogger(VehicleController::class.java.simpleName)
    }

    @PostMapping
    @Operation(summary = "Cria um veículo na base de dados",
            responses = [
                ApiResponse(responseCode = "201", description = "Veículo criado"),
                ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
                ApiResponse(responseCode = "404", description = "Modelo ou marca não encontrados")]
    )
    fun createVehicle(@Valid @RequestBody command: CreateVehicleCommand): VehicleDTO {
        log.info("I=init, command=$command")
        return VehicleDTO.from(facade.create(command)).also {
            log.info("I=finish, vehicle=$it")
        }
    }

    @GetMapping("/modelos")
    @Operation(summary = "Lista todos os modelos de veículos e respectivas marcas cadastrados",
            responses = [
                ApiResponse(responseCode = "200", description = "Modelos listados")
            ]
    )
    fun listModels(): List<VehicleModelDTO> {
        return VehicleModelDTO.from(facade.listModels())
    }

    @GetMapping
    @Operation(summary = "Lista todos os veículos cadastrados",
            responses = [
                ApiResponse(responseCode = "200", description = "Veículos listados")
            ]
    )
    fun listVehicles(): List<VehicleDTO> {
        return VehicleDTO.from(facade.listVehicles())
    }
}
