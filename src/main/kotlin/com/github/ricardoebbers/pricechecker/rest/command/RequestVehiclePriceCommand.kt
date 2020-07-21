package com.github.ricardoebbers.pricechecker.rest.command

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@JsonIgnoreProperties(ignoreUnknown = true)
data class RequestVehiclePriceCommand(
        @field:Pattern(regexp = "\\w{3}-\\d{4}")
        @field:JsonProperty(value = "placa")
        val licensePlate: String,

        @field:NotNull
        @field:Positive
        @field:JsonProperty(value = "id_modelo")
        val modelId: Int,

        @field:Positive
        @field:JsonProperty(value = "id_marca")
        val brandId: Int?,

        @field:NotNull
        @field:PositiveOrZero
        @field:JsonProperty(value = "preco_anuncio")
        val marketPrice: BigDecimal,

        @field:NotNull
        @field:Positive
        @field:JsonProperty(value = "ano")
        val year: Int
) {
    @JsonIgnore
    fun toEntity() = Vehicle(
            licensePlate = licensePlate.toUpperCase(),
            marketPrice = marketPrice,
            year = year
    )
}
