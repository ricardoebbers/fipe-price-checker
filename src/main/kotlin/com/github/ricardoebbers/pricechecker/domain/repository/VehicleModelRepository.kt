package com.github.ricardoebbers.pricechecker.domain.repository

import com.github.ricardoebbers.pricechecker.domain.entity.VehicleModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleModelRepository : JpaRepository<VehicleModel, String> {
    fun findOneByFipeId(fipeId: Int): VehicleModel
}
