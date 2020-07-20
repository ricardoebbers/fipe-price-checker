package com.github.ricardoebbers.pricechecker.domain.service.impl

import com.github.ricardoebbers.pricechecker.domain.entity.VehicleModel
import com.github.ricardoebbers.pricechecker.domain.exception.BrandAndModelDontMatchException
import com.github.ricardoebbers.pricechecker.domain.exception.EntityNotFoundException
import com.github.ricardoebbers.pricechecker.domain.repository.VehicleModelRepository
import com.github.ricardoebbers.pricechecker.domain.service.VehicleModelService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class VehicleModelServiceImpl(
        private val repository: VehicleModelRepository
) : VehicleModelService {

    companion object {
        private val log = Logger.getLogger(VehicleModelServiceImpl::class.java.simpleName)
    }

    override fun listModels(): List<VehicleModel> {
        return repository.findAll()
    }

    override fun getModelByFipeId(modelId: Int, brandId: Int?): VehicleModel {
        return try {
            repository.findOneByFipeId(modelId).also {model ->
                validateBrandAndModel(model, brandId ?: model.brandFipeId(), modelId)
            }
        } catch (erdae: EmptyResultDataAccessException) {
            log.warning("W=vehicle_model_not_found, fipeId=$modelId")
            throw EntityNotFoundException("Modelo de veículo", modelId)
        }
    }

    private fun validateBrandAndModel(it: VehicleModel, brandId: Int, modelId: Int) {
        if (it.brand.fipeId != brandId) {
            log.warning("W=brand_and_model_dont_match," +
                    "modelId=$modelId, actualBrandId=${it.brand.fipeId}," +
                    "informedBrandId=$brandId")
            throw BrandAndModelDontMatchException(it.brand.fipeId, brandId, modelId)
        }
    }
}
