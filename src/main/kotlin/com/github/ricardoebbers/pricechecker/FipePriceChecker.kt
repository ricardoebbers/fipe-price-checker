package com.github.ricardoebbers.pricechecker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FipePriceChecker

fun main(args: Array<String>) {
	runApplication<FipePriceChecker>(*args)
}
