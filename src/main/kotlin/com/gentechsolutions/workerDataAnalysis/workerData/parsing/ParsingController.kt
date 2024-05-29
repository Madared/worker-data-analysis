package com.gentechsolutions.workerDataAnalysis.workerData.parsing

import com.gentechsolutions.workerDataAnalysis.workerData.parsing.files.WorkerDataFile
import io.swagger.v3.oas.annotations.Operation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

abstract class HttpResult {
    abstract val succeeded: Boolean
}

sealed class ParsingHttpResult() : HttpResult()
class SuccessfulParsingResult(val workerDataParsingResults: WorkerDataParsingResults) : ParsingHttpResult() {
    override val succeeded = true
}

class FailedParsingResult(val error: String) : ParsingHttpResult() {
    override val succeeded = false
}

@RestController
@RequestMapping("parsing", produces = ["application/json"])
class ParsingController(
    private val parsingService: FileParsingService,
) {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Operation(summary = "Parses file to get a list of DataSet")
    @PostMapping("/parse")
    fun parse(
        @RequestBody(required = true) file: WorkerDataFile,
    ): ResponseEntity<ParsingHttpResult> {
        val parsed = parsingService.parse(file)
        if (parsed.isSuccess) {
            val success = SuccessfulParsingResult(parsed.getOrThrow())
            return ResponseEntity.ok().body(success)
        }
        val error = parsed.exceptionOrNull()!!
        logger.atError().log(error.message)
        val failure = FailedParsingResult(error.message ?: "Unknown error")
        return if (error is HttpException) {
            error.mapToResponseEntity(failure)
        } else {
            ResponseEntity.internalServerError().body(failure)
        }
    }
}

abstract class HttpException(message: String) : Exception(message) {
    abstract fun <T> mapToResponseEntity(data: T): ResponseEntity<T>
}
