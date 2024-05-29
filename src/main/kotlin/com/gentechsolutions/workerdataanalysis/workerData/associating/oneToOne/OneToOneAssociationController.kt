package com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne

import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData
import com.gentechsolutions.workerDataAnalysis.workerData.comparing.WorkerDataComparerType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class OneToOneAssociationRequest(
    val internal: List<WorkerData>,
    val external: List<WorkerData>,
    val comparers: List<WorkerDataComparerType>
)

@RestController("association/one-to-one")
class OneToOneAssociationController(
    private val associationService: OneToOneAssociationService
) {
    @GetMapping("associate")
    fun associate(@RequestBody request: OneToOneAssociationRequest): ResponseEntity<OneToOneAssociationResults> {
        val results = associationService.associate(request.internal, request.external, request.comparers)
        return ResponseEntity.ok().body(results)
    }
}