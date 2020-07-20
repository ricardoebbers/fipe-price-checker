package com.github.ricardoebbers.pricechecker.domain.service

import com.github.ricardoebbers.pricechecker.domain.entity.VehicleModel

interface VehicleModelService {
    fun listModels(): List<VehicleModel>
    fun getModelByFipeId(modelId: Int, brandId: Int?): VehicleModel
}
