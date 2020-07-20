package com.github.ricardoebbers.pricechecker.rest.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import java.math.BigDecimal
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class VehicleDTO(
        @field:JsonProperty(value = "placa")
        val licensePlate: String,
        @field:JsonProperty(value = "preco_anuncio")
        val marketPrice: BigDecimal,
        @field:JsonProperty(value = "ano")
        val year: Int,
        @field:JsonProperty(value = "preco_fipe")
        val fipePrice: BigDecimal?,
        @field:JsonProperty(value = "data_cadastro")
        val createDate: LocalDate,
        @field:JsonProperty(value = "modelo")
        val model: String?,
        @field:JsonProperty(value = "marca")
        val brand: String?
) {
    companion object {
        fun from(entity: Vehicle) = VehicleDTO(
                licensePlate = entity.licensePlate,
                marketPrice = entity.marketPrice,
                year = entity.year,
                fipePrice = entity.fipePrice,
                createDate = entity.createDate,
                model = entity.modelName(),
                brand = entity.brandName()
        )

        fun from(entityList: List<Vehicle>) = entityList.map { from(it) }
    }
}
