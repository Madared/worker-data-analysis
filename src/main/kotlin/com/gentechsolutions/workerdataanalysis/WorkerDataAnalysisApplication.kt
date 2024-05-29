package com.gentechsolutions.workerdataanalysis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkerDataAnalysisApplication

fun main(args: Array<String>) {
    runApplication<WorkerDataAnalysisApplication>(*args)
}
