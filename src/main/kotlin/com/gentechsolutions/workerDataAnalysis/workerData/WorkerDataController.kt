package com.gentechsolutions.workerDataAnalysis.workerData

import com.gentechsolutions.workerDataAnalysis.HttpException
import com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne.OneToOneAssociationRequest
import com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne.OneToOneAssociationResults
import com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne.OneToOneAssociationService
import com.gentechsolutions.workerDataAnalysis.workerData.filtering.FilteringRequest
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.*
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.files.WorkerDataFile
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController("/worker-data")
class WorkerDataController(
    private val associationService: OneToOneAssociationService,
    private val parsingService: FileParsingService
) {
    private val logger = LoggerFactory.getLogger(WorkerDataController::class.java)

    @GetMapping("/filter")
    fun filter(@RequestBody filteringRequest: FilteringRequest): ResponseEntity<List<WorkerData>> {
        val filter = filteringRequest.filter
        val data = filteringRequest.data
        val filtered = filter.filter(data)
        return ResponseEntity.ok(filtered)
    }

    @GetMapping("/associate")
    fun associate(@RequestBody associationRequest: OneToOneAssociationRequest): ResponseEntity<OneToOneAssociationResults> {
        val comparers = associationRequest.comparers
        val associated =
            associationService.associate(associationRequest.internal, associationRequest.external, comparers)
        return ResponseEntity.ok(associated)
    }

    @GetMapping("/parse")
    fun parse(@RequestBody file: WorkerDataFile): ResponseEntity<WorkerDataParsingResults> {
        val parsed = parsingService.parse(file)
        if (parsed.isSuccess) {
            return ResponseEntity.ok(parsed.getOrThrow())
        }
        val error = parsed.exceptionOrNull()!!
        logger.atError().log(error.message)
        return ResponseEntity.internalServerError().build<WorkerDataParsingResults>()
    }
}