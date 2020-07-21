package com.github.ricardoebbers.pricechecker.rest.client.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
data class FipePriceDTO(
        @JsonProperty(value = "preco")
        private val priceString: String
) {
    val price: BigDecimal = BigDecimal(priceString
            .replace("R$", "")
            .replace(".", "")
            .replace(",", ".")
            .trim())
}
