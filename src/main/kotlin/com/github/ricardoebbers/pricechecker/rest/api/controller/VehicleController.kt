package com.github.ricardoebbers.pricechecker.rest.api.controller

import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import com.github.ricardoebbers.pricechecker.domain.facade.VehicleFacade
import com.github.ricardoebbers.pricechecker.rest.api.command.RequestVehiclePriceCommand
import com.github.ricardoebbers.pricechecker.rest.api.dto.VehicleDTO
import com.github.ricardoebbers.pricechecker.rest.api.dto.VehicleModelDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger
import javax.validation.Valid
import javax.validation.constraints.Pattern

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
                ApiResponse(responseCode = "202", description = "Solicitação de criação de veículo registrada"),
                ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
                ApiResponse(responseCode = "404", description = "Modelo ou marca não encontrados")]
    )
    fun createVehicle(@Valid @RequestBody command: RequestVehiclePriceCommand): ResponseEntity<Unit> {
        log.info("I=init, command=$command")
        facade.requestPrice(command)
        return ResponseEntity.accepted().build()
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

    @GetMapping("/{licensePlate}")
    @Operation(summary = "Busca veículo pela placa cadastrada",
            responses = [
                ApiResponse(responseCode = "200", description = "Veículo encontrado"),
                ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
                ApiResponse(responseCode = "400", description = "Consulta inválida")]
    )
    fun findByLicensePlate(@PathVariable
                           @Pattern(regexp = "\\w{3}-\\d{4}")
                           licensePlate: String): ResponseEntity<Vehicle> {
        return facade.findByLicensePlate(licensePlate)?.let {
            ResponseEntity.ok().body(it)
        } ?: ResponseEntity.notFound().build()

    }
}
