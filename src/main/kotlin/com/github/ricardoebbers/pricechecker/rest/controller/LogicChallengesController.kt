package com.github.ricardoebbers.pricechecker.rest.controller

import com.github.ricardoebbers.pricechecker.domain.service.LogicChallengesService
import com.github.ricardoebbers.pricechecker.rest.dto.MoneyBillsDTO
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger
import java.util.logging.Logger
import javax.validation.constraints.Max
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@RestController
@RequestMapping("/v1/challenges")
@Validated
class LogicChallengesController(
        private val service: LogicChallengesService
) {
    companion object {
        private val log = Logger.getLogger(LogicChallengesController::class.java.simpleName)
    }

    @GetMapping("/fibonacci/{nthNumber}")
    fun getFibonacciNthNumber(@PathVariable @NotNull @PositiveOrZero @Max(1000) nthNumber: Int): BigInteger {
        log.info("I=init, nthNumber=$nthNumber")
        return service.fibonacciNthNumber(nthNumber).also {
            log.info("I=finish, result=$it")
        }
    }

    @GetMapping("/changeCalculator/least/{changeValue}")
    fun getLeastMoneyBillsForChange(@PathVariable @NotNull @PositiveOrZero changeValue: Int): MoneyBillsDTO {
        log.info("I=init, changeValue=$changeValue")
        return MoneyBillsDTO.from(service.leastMoneyBillChange(changeValue)).also {
            log.info("I=finish, result=$it")
        }
    }

    @PostMapping("/sumEvenNumbers")
    fun getSumOfEvenNumbers(@RequestBody @NotNull numbers: List<Int>): Int {
        log.info("I=init, numbers=$numbers")
        return service.sumEvenNumbers(numbers).also {
            log.info("I=finish, result=$it")
        }
    }

}
