package com.github.ricardoebbers.pricechecker.domain.repository

import com.github.ricardoebbers.pricechecker.domain.entity.Vehicle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository : JpaRepository<Vehicle, String>
