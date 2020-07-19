package com.github.ricardoebbers.pricechecker.rest.controller

import com.github.ricardoebbers.pricechecker.domain.service.LogicChallengesService
import com.github.ricardoebbers.pricechecker.rest.dto.MoneyBillsDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
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
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@RestController
@RequestMapping("/v1/challenges")
@Validated
@Tag(name = "Desafios de lógica")
class LogicChallengesController(
        private val service: LogicChallengesService
) {
    companion object {
        private val log = Logger.getLogger(LogicChallengesController::class.java.simpleName)
    }

    @GetMapping("/fibonacci/{nthNumber}")
    @Operation(summary = "Calcula o n-ésimo número da sequência fibonacci")
    fun getFibonacciNthNumber(@PathVariable
                              @NotNull
                              @PositiveOrZero
                              @Max(1000)
                              @Parameter(description = "Número inteiro entre 0 e 1000, ex: 42")
                              nthNumber: Int
    ): BigInteger {
        log.info("I=init, nthNumber=$nthNumber")
        return service.fibonacciNthNumber(nthNumber).also {
            log.info("I=finish, result=$it")
        }
    }

    @GetMapping("/least-change-calculator/{changeValue}")
    @Operation(summary = "Calcula o mínimo de cédulas possível para determinado valor")
    fun getLeastMoneyBillsForChange(@PathVariable
                                    @NotNull
                                    @PositiveOrZero
                                    @Parameter(description = "Número inteiro positivo, ex: 188")
                                    changeValue: Int): MoneyBillsDTO {
        log.info("I=init, changeValue=$changeValue")
        return MoneyBillsDTO.from(service.leastMoneyBillChange(changeValue)).also {
            log.info("I=finish, result=$it")
        }
    }

    @PostMapping("/sum-even-numbers")
    @Operation(summary = "Soma os números pares dada lista de números inteiros")
    fun getSumOfEvenNumbers(@RequestBody
                            @NotNull
                            @SwaggerRequestBody(description = "Lista de números inteiros, ex: [1,2,3,4,5,6]")
                            numbers: List<Int>): Int {
        log.info("I=init, numbers=$numbers")
        return service.sumEvenNumbers(numbers).also {
            log.info("I=finish, result=$it")
        }
    }

}
