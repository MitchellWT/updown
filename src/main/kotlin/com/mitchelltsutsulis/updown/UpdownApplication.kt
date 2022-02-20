package com.mitchelltsutsulis.updown

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UpdownApplication

fun main(args: Array<String>) {
	runApplication<UpdownApplication>(*args)
}
