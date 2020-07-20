package com.github.ricardoebbers.pricechecker.rest.dto

import com.github.ricardoebbers.pricechecker.domain.entity.VehicleModel

data class VehicleModelDTO(
        val id_fipe_modelo: Int,
        val id_fipe_marca: Int,
        val nome_modelo: String,
        val nome_marca: String
) {
    companion object {
        fun from(entity: VehicleModel) = VehicleModelDTO(
                id_fipe_modelo = entity.fipeId,
                id_fipe_marca = entity.brandFipeId(),
                nome_modelo = entity.name,
                nome_marca = entity.brandName()
        )

        fun from(entityList: List<VehicleModel>) = entityList.map { from(it) }
    }
}
