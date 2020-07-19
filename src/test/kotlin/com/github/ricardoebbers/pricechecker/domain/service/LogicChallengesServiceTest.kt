package com.github.ricardoebbers.pricechecker.domain.service

import com.github.ricardoebbers.pricechecker.domain.enums.MoneyBill
import com.github.ricardoebbers.pricechecker.domain.service.impl.LogicChallengesServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class LogicChallengesServiceTest {

    private val service = LogicChallengesServiceImpl()

    @ParameterizedTest
    @CsvSource(value = [
        "0:0",
        "1:1",
        "2:1",
        "3:2",
        "4:3",
        "5:5",
        "6:8"
    ], delimiter = ':')
    fun fibonacciNthNumber_zeroOrPositiveN(n: Int, expected: Long) {
        assertEquals(expected, service.fibonacciNthNumber(n))
    }

    @Test
    fun fibonacciNthNumber_negativeN() {
        assertEquals(0, service.fibonacciNthNumber(-10))
    }

    @ParameterizedTest
    @CsvSource(value = [
        "100:[1,0,0,0,0,0,0]",
        "200:[2,0,0,0,0,0,0]",
        "188:[1,1,1,1,1,1,1]"
    ], delimiter = ':')
    fun leastMoneyBillChange_whenPositive(change: Int, expectedBillsQty: String) {
        val result = service.leastMoneyBillChange(change)
        val moneyBills = splitStringToIntList(expectedBillsQty)
        assertEquals(change, result.map { it.key.faceValue * it.value }.sum())
        assertEquals(moneyBills[0], result.getOrDefault(MoneyBill.HUNDRED, 0))
        assertEquals(moneyBills[1], result.getOrDefault(MoneyBill.FIFTY, 0))
        assertEquals(moneyBills[2], result.getOrDefault(MoneyBill.TWENTY, 0))
        assertEquals(moneyBills[3], result.getOrDefault(MoneyBill.TEN, 0))
        assertEquals(moneyBills[4], result.getOrDefault(MoneyBill.FIVE, 0))
        assertEquals(moneyBills[5], result.getOrDefault(MoneyBill.TWO, 0))
        assertEquals(moneyBills[6], result.getOrDefault(MoneyBill.ONE, 0))
    }

    @Test
    fun leastMoneyBillChange_whenNegative() {
        val change = -188
        val result = service.leastMoneyBillChange(change)
        assertEquals(0, result.map { it.key.faceValue * it.value }.sum())
    }

    @ParameterizedTest
    @CsvSource(value = [
        "20:[6,2,7,0,5,8,4]",
        "20:[1,2,3,4,5,6,7,8,9]",
        "0:[1,1,1,1,1,1,1]"
    ], delimiter = ':')
    fun sumEvenNumbers(expected: Int, sequence: String) {
        val result = service.sumEvenNumbers(splitStringToIntList(sequence))
        assertEquals(expected, result)
    }

    private fun splitStringToIntList(expectedBillsQty: String): List<Int> {
        return expectedBillsQty
                .split("[", ",", "]")
                .filter { it.isNotBlank() }
                .map { it.trim().toInt() }
                .toList()
    }
}
