package com.github.ricardoebbers.pricechecker.domain.service.impl

import com.github.ricardoebbers.pricechecker.domain.enums.MoneyBill
import com.github.ricardoebbers.pricechecker.domain.service.LogicChallengesService
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.util.logging.Logger

@Service
class LogicChallengesServiceImpl : LogicChallengesService {
    companion object {
        private val log = Logger.getLogger(this::class.java.simpleName)
        private val fibonacciCache = mutableMapOf<Int, BigInteger>()
    }

    override fun fibonacciNthNumber(n: Int): BigInteger {
        log.info("I=init, n=$n")
        memoizedRecursiveFibonacci(n)
        log.info("I=finish, calculated_values=$fibonacciCache, result=${fibonacciCache[n]}")
        return fibonacciCache[n] ?: BigInteger.ZERO
    }

    private fun memoizedRecursiveFibonacci(n: Int): BigInteger {
        return when {
            n <= 0 -> BigInteger.ZERO.also { fibonacciCache.putIfAbsent(n, it) }
            n <= 2 -> BigInteger.ONE.also { fibonacciCache.putIfAbsent(n, it) }
            fibonacciCache[n] != null -> fibonacciCache.getOrDefault(n, BigInteger.ZERO)
            else -> (memoizedRecursiveFibonacci(n - 1) +
                    memoizedRecursiveFibonacci(n - 2)).also {
                log.info("I=calculated, n=$n, value=$it")
                fibonacciCache.putIfAbsent(n, it)
            }
        }
    }

    override fun leastMoneyBillChange(changeValue: Int): Map<MoneyBill, Int> {
        log.info("I=init, changeValue=$changeValue")
        val result = mutableMapOf<MoneyBill, Int>()
        if (changeValue <= 0) {
            log.warning("W=returning_empty")
        } else {
            separeIntoBills(changeValue, result)
        }
        log.info("I=finish, result=$result")
        return result
    }

    private fun separeIntoBills(changeValue: Int, result: MutableMap<MoneyBill, Int>) {
        var remainder = changeValue
        MoneyBill.values().forEach {
            log.info("I=calculating_for, remainder=$remainder, this_bill=$it")
            if (remainder >= it.faceValue) {
                val billQuantity = remainder / it.faceValue
                result.merge(it, billQuantity) { old, new -> old + new }
                remainder -= it.faceValue * result.getOrDefault(it, 0)
            }
        }
    }

    override fun sumEvenNumbers(numbersSequence: List<Int>): Int {
        log.info("I=init, numbersSequence=$numbersSequence")
        return numbersSequence.filter { it % 2 == 0 }.sum().also {
            log.info("I=finish, result=$it")
        }
    }
}
