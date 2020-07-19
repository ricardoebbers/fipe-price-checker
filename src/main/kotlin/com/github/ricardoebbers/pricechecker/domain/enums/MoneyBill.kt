package com.github.ricardoebbers.pricechecker.domain.enums

enum class MoneyBill(val faceValue: Int, val localizedName: String) {
    HUNDRED(100, "Cem"),
    FIFTY(50, "Cinquenta"),
    TWENTY(20, "Vinte"),
    TEN(10, "Dez"),
    FIVE(5, "Cinco"),
    TWO(2, "Dois"),
    ONE(1, "Um")
}
