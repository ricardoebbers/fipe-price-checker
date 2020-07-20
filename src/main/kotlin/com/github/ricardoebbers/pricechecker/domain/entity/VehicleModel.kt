package com.github.ricardoebbers.pricechecker.domain.entity

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "modelo")
data class VehicleModel(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(
                name = "UUID",
                strategy = "org.hibernate.id.UUIDGenerator"
        )
        var id: String? = null,
        val name: String,
        val fipeId: Int,
        @ManyToOne(fetch= FetchType.LAZY)
        @JoinColumn(name="id_marca", nullable=false)
        val brand: VehicleBrand
) {
        val brandName = brand.name
}
