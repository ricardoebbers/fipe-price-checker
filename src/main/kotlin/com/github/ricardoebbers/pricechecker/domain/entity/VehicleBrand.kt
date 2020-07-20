package com.github.ricardoebbers.pricechecker.domain.entity

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "marca")
data class VehicleBrand(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(
                name = "UUID",
                strategy = "org.hibernate.id.UUIDGenerator"
        )
        var id: String? = null,
        val name: String,
        val fipeId: Int
)
