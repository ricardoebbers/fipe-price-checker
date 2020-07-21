package com.github.ricardoebbers.pricechecker.rest.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.ricardoebbers.pricechecker.domain.enums.MoneyBill
import com.github.ricardoebbers.pricechecker.domain.service.LogicChallengesService
import com.github.ricardoebbers.pricechecker.rest.api.controller.LogicChallengesController
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigInteger


@WebMvcTest(controllers = [LogicChallengesController::class])
internal class LogicChallengesControllerTest {
    @MockkBean
    private lateinit var service: LogicChallengesService

    @Autowired
    private lateinit var webMvc: MockMvc

    companion object {
        private const val BASE_URL = "/v1/challenges"
        private const val GET_FIBONACCI_URL = "$BASE_URL/fibonacci/{nthNumber}"
        private const val GET_LEAST_CHANGE_URL = "$BASE_URL/least-change-calculator/{changeValue}"
        private const val POST_SUM_EVEN_NUMBERS_URL = "$BASE_URL/sum-even-numbers"
        private fun asJsonString(obj: Any): String {
            return try {
                ObjectMapper().writeValueAsString(obj)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }

    @ParameterizedTest
    @CsvSource(value = [
        "0:0",
        "1:1",
        "2:1",
        "3:2",
        "4:3",
        "5:5",
        "6:8",
        "50:12586269025",
        "500:139423224561697880139724382870407283950070256587697307264108962948325571622863290691557658876222521294125",
        "1000:43466557686937456435688527675040625802564660517371780402481729089536555417949051890403879840079255169295922593080322634775209689623239873322471161642996440906533187938298969649928516003704476137795166849228875"
    ], delimiter = ':')
    fun getFibonacciNthNumber_returns200_whenNumberIsWithinRange(number: Int, expected: BigInteger) {
        every { service.fibonacciNthNumber(number) } returns expected
        webMvc.perform(get(GET_FIBONACCI_URL, number)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$").value(expected))
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 1001])
    fun getFibonacciNthNumber_returns400_whenNumberIsNegativeOrGreaterThan1000(number: Int) {
        webMvc.perform(get(GET_FIBONACCI_URL, number)
                .accept(APPLICATION_JSON))
                .andExpect(status().`is`(400))
                .andExpect(jsonPath("$.message").isString)
                .andExpect(jsonPath("$.message").isNotEmpty)
                .andExpect(jsonPath("$.status").isString)
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name))
    }

    @ParameterizedTest
    @ValueSource(strings = ["NaN", "[]", "{}"])
    fun getFibonacciNthNumber_returns400_whenNotANumberIsPassed(number: Any?) {
        webMvc.perform(get(GET_FIBONACCI_URL, number)
                .accept(APPLICATION_JSON))
                .andExpect(status().`is`(400))
                .andExpect(jsonPath("$.message").isString)
                .andExpect(jsonPath("$.message").isNotEmpty)
                .andExpect(jsonPath("$.status").isString)
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name))
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 188, 345])
    fun getLeastMoneyBillsForChange_returns200_whenANumberPositiveOrZeroIsPassed(number: Int) {
        every { service.leastMoneyBillChange(number) } returns mapOf(MoneyBill.HUNDRED to 1)
        webMvc.perform(get(GET_LEAST_CHANGE_URL, number)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.cedulas").isMap)
                .andExpect(jsonPath("$.cedulas").isNotEmpty)
    }

    @Test
    fun getLeastMoneyBillsForChange_returns400_whenANegativeNumberIsPassed() {
        every { service.leastMoneyBillChange(any()) } returns mapOf(MoneyBill.HUNDRED to 1)
        webMvc.perform(get(GET_LEAST_CHANGE_URL, -5)
                .accept(APPLICATION_JSON))
                .andExpect(status().`is`(400))
                .andExpect(jsonPath("$.message").isString)
                .andExpect(jsonPath("$.message").isNotEmpty)
                .andExpect(jsonPath("$.status").isString)
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name))
    }


    @Test
    fun getLeastMoneyBillsForChange_returns400_whenNotANumberIsPassed() {
        every { service.leastMoneyBillChange(any()) } returns mapOf(MoneyBill.HUNDRED to 1)
        webMvc.perform(get(GET_LEAST_CHANGE_URL, "Not A Number")
                .accept(APPLICATION_JSON))
                .andExpect(status().`is`(400))
                .andExpect(jsonPath("$.message").isString)
                .andExpect(jsonPath("$.message").isNotEmpty)
                .andExpect(jsonPath("$.status").isString)
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name))
    }

    @Test
    fun getSumOfEvenNumbers_returns200_whenAnArrayOfIntsIsPassed() {
        val input = listOf(1, 2, 5, -4, 3, 12, 58, 0, -111)
        every { service.sumEvenNumbers(input) } returns 68
        webMvc.perform(post(POST_SUM_EVEN_NUMBERS_URL)
                .content(asJsonString(input))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$").isNumber)
                .andExpect(jsonPath("$").value(68))
    }

    @Test
    fun getSumOfEvenNumbers_returns400_whenAnArrayOfNotIntsIsPassed() {
        val input = listOf(3.1, 10 / 3, "NaN", null)
        webMvc.perform(post(POST_SUM_EVEN_NUMBERS_URL)
                .content(asJsonString(input))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().`is`(400))
                .andExpect(jsonPath("$.message").isString)
                .andExpect(jsonPath("$.message").isNotEmpty)
                .andExpect(jsonPath("$.status").isString)
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name))
    }
}
