package com.github.ricardoebbers.pricechecker.rest.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.ricardoebbers.pricechecker.domain.entity.VehicleModel

data class VehicleModelDTO(
        @field:JsonProperty(value = "id_fipe_modelo")
        val modelId: Int,
        @field:JsonProperty(value = "id_fipe_marca")
        val brandId: Int,
        @field:JsonProperty(value = "nome_modelo")
        val modelName: String,
        @field:JsonProperty(value = "nome_marca")
        val brandName: String
) {
    companion object {
        fun from(entity: VehicleModel) = VehicleModelDTO(
                modelId = entity.fipeId,
                brandId = entity.brandFipeId(),
                modelName = entity.name,
                brandName = entity.brandName()
        )

        fun from(entityList: List<VehicleModel>) = entityList.map { from(it) }
    }
}
