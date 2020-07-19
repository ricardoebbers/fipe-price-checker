package com.github.ricardoebbers.pricechecker.rest.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.github.ricardoebbers.pricechecker.domain.enums.MoneyBill

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MoneyBillsDTO(
        val cedulas: Map<String, Int>
) {
    companion object {
        fun from(moneyBillsQuantityMap: Map<MoneyBill, Int>) = MoneyBillsDTO(
                cedulas = moneyBillsQuantityMap
                        .toSortedMap(compareByDescending { it.faceValue })
                        .map { Pair(it.key.localizedName, it.value) }.toMap()
        )
    }
}
