package com.github.ricardoebbers.pricechecker.infrastructure.config

import com.github.ricardoebbers.pricechecker.domain.repository.VehicleRepository
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackageClasses = [VehicleRepository::class])
@EnableJpaAuditing
class PersistenceConfig {
}
