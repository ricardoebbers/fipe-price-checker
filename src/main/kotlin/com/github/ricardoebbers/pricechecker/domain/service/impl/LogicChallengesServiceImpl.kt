package com.github.ricardoebbers.pricechecker.domain.service.impl

import com.github.ricardoebbers.pricechecker.domain.enums.MoneyBill
import com.github.ricardoebbers.pricechecker.domain.service.LogicChallengesService
import org.springframework.stereotype.Service

@Service
class LogicChallengesServiceImpl : LogicChallengesService {
    override fun fibonacciNthNumber(n: Int): Long {
        val cache = mutableMapOf<Int, Long>()
        memoizedRecursiveFibonacci(n, cache)
        return cache[n] ?: 0L
    }

    override fun leastMoneyBillChange(changeValue: Int): Map<MoneyBill, Int> {
        val result = mutableMapOf<MoneyBill, Int>()
        return if (changeValue <= 0) {
            result
        } else {
            var remainder = changeValue
            MoneyBill.values().forEach {
                if (remainder >= it.faceValue) {
                    val billQuantity = remainder / it.faceValue
                    result.merge(it, billQuantity) { old, new -> old + new }
                    remainder -= it.faceValue * result.getOrDefault(it, 0)
                }
            }
            result
        }
    }

    override fun sumEvenNumbers(numbersSequence: List<Int>): Int {
        return numbersSequence.filter { it % 2 == 0 }.sum()
    }

    private fun memoizedRecursiveFibonacci(n: Int, cache: MutableMap<Int, Long>): Long {
        return when {
            n <= 0 -> {
                cache[n] = 0
                0
            }
            n <= 2 -> {
                cache[n] = 1
                1
            }
            else -> {
                val calculated = (memoizedRecursiveFibonacci(n - 1, cache)
                        + memoizedRecursiveFibonacci(n - 2, cache))
                cache[n] = calculated
                calculated
            }
        }
    }
}
