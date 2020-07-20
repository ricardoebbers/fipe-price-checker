package com.github.ricardoebbers.pricechecker.rest.command

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateVehicleCommand(
        @field:Pattern(regexp = "\\w{3}-\\d{4}")
        private val placa: String,

        @field:NotNull
        @field:Positive
        private val id_modelo: Int,

        @field:Positive
        private val id_marca: Int?,

        @field:NotNull
        @field:PositiveOrZero
        private val preco_anuncio: BigDecimal,

        @field:NotNull
        @field:Positive
        private val ano: Int
) {
    @JsonIgnore
    fun toEntity() = Vehicle(
            licensePlate = placa.toUpperCase(),
            marketPrice = preco_anuncio,
            year = ano
    )

    @JsonIgnore
    val modelId = id_modelo

    @JsonIgnore
    val brandId = id_marca
}
