package com.github.ricardoebbers.pricechecker.domain.entity

import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "veiculo")
@EntityListeners(AuditingEntityListener::class)
data class Vehicle(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(
                name = "UUID",
                strategy = "org.hibernate.id.UUIDGenerator"
        )
        var id: String? = null,
        @Column(name = "placa", unique = true, nullable = false)
        val licensePlate: String,
        @Column(name = "preco_anuncio", nullable = false)
        val marketPrice: BigDecimal,
        @Column(name = "ano", nullable = false)
        val year: Int,
        @Column(name="preco_fipe")
        var fipePrice: BigDecimal? = null,
        @ManyToOne(fetch= FetchType.LAZY)
        @JoinColumn(name="id_marca", nullable=false)
        val model: VehicleModel,
        @CreatedDate
        val createDate: LocalDate
)
