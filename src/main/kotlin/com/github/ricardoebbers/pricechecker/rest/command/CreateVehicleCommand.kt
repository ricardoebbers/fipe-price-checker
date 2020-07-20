package com.github.ricardoebbers.pricechecker.rest.command

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateVehicleCommand(
        @NotNull
        @NotBlank
        @Pattern(regexp = "\\w{3}-\\d{4}")
        val placa: String,

        @NotEmpty
        @PositiveOrZero
        val id_modelo: Int,

        @NotEmpty
        @PositiveOrZero
        val id_marca: Int,

        @NotEmpty
        @PositiveOrZero
        val preco_anuncio: BigDecimal,

        @NotEmpty
        @Positive
        val ano: Int
) {
    fun toEntity() = Vehicle(
            licensePlate = placa,
            marketPrice = preco_anuncio,
            year = ano
    )
}
