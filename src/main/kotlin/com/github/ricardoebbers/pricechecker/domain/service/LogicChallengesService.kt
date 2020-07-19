package com.github.ricardoebbers.pricechecker.domain.service

import com.github.ricardoebbers.pricechecker.domain.enums.MoneyBill

interface LogicChallengesService {
    fun fibonacciNthNumber(n: Int): Long
    fun leastMoneyBillChange(changeValue: Int): Map<MoneyBill, Int>
    fun sumEvenNumbers(numbersSequence: List<Int>): Int
}
