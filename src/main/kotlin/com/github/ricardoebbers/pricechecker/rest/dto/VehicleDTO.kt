package com.github.ricardoebbers.pricechecker.rest.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import java.math.BigDecimal
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class VehicleDTO(
        val placa: String,
        val preco_anuncio: BigDecimal,
        val ano: Int,
        val preco_fipe: BigDecimal?,
        val data_cadastro: LocalDate,
        val modelo: String?,
        val marca: String?
) {
    companion object {
        fun from(entity: Vehicle) = VehicleDTO(
                placa = entity.licensePlate,
                preco_anuncio = entity.marketPrice,
                ano = entity.year,
                preco_fipe = entity.fipePrice,
                data_cadastro = entity.createDate,
                modelo = entity.modelName(),
                marca = entity.brandName()
        )

        fun from(entityList: List<Vehicle>) = entityList.map { from(it) }
    }
}
