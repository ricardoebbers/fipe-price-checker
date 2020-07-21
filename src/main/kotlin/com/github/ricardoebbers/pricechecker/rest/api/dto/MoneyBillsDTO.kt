package com.github.ricardoebbers.pricechecker.rest.api.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.ricardoebbers.pricechecker.domain.enums.MoneyBill

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MoneyBillsDTO(
        @field:JsonProperty(value = "cedulas")
        val bills: Map<String, Int>
) {
    companion object {
        fun from(moneyBillsQuantityMap: Map<MoneyBill, Int>) = MoneyBillsDTO(
                bills = moneyBillsQuantityMap
                        .toSortedMap(compareByDescending { it.faceValue })
                        .map { Pair(it.key.localizedName, it.value) }.toMap()
        )
    }
}
