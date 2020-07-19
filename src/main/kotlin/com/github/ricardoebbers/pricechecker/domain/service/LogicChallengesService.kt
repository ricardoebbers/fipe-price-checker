package com.github.ricardoebbers.pricechecker.domain.service

import com.github.ricardoebbers.pricechecker.domain.enums.MoneyBill
import java.math.BigInteger

interface LogicChallengesService {
    fun fibonacciNthNumber(n: Int): BigInteger
    fun leastMoneyBillChange(changeValue: Int): Map<MoneyBill, Int>
    fun sumEvenNumbers(numbersSequence: List<Int>): Int
}
