package com.github.ricardoebbers.pricechecker.messaging.message

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import java.io.Serializable
import java.math.BigDecimal

data class VehicleMessage(
        val licensePlate: String,
        val modelFipeId: Int,
        val brandFipeId: Int,
        val marketPrice: BigDecimal,
        var fipePrice: BigDecimal?,
        val year: Int
) : Serializable {
    companion object {

        @JsonCreator
        @JvmStatic
        fun fromJson(json: String): VehicleMessage = ObjectMapper().readValue(json)

        fun from(entity: Vehicle): VehicleMessage {
            require(entity.model != null)
            return VehicleMessage(
                    licensePlate = entity.licensePlate,
                    modelFipeId = entity.modelFipeId()!!,
                    brandFipeId = entity.brandFipeId()!!,
                    marketPrice = entity.marketPrice,
                    fipePrice = entity.fipePrice,
                    year = entity.year
            )
        }
    }

    fun toEntity() = Vehicle(
            licensePlate = licensePlate.toUpperCase(),
            marketPrice = marketPrice,
            fipePrice = fipePrice,
            year = year
    )
}
