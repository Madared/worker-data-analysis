package com.gentechsolutions.workerDataAnalysis.hoursDifference.reporting

import com.gentechsolutions.workerDataAnalysis.hoursDifference.HoursDifferenceRequest
import com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne.OneToOneAssociationService
import jakarta.servlet.http.HttpServletResponse
import org.apache.poi.util.IOUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayInputStream

@RestController("/hours-difference")
class HoursDifferenceController(private val associationService: OneToOneAssociationService) {
    @GetMapping("/report")
    fun report(@RequestBody hoursDifferenceRequest: HoursDifferenceRequest, response: HttpServletResponse) {
        val filteredInternal = hoursDifferenceRequest.dataFilter?.filter(hoursDifferenceRequest.internal)
            ?: hoursDifferenceRequest.internal
        val filteredExternal = hoursDifferenceRequest.dataFilter?.filter(hoursDifferenceRequest.external)
            ?: hoursDifferenceRequest.external
        val associated = associationService.associate(
            filteredInternal,
            filteredExternal,
            hoursDifferenceRequest.comparers
        )
        val report = ReportGeneratorExcel().generate(associated)
        response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        val inputStream = ByteArrayInputStream(report.toByteArray())
        IOUtils.copy(inputStream, response.outputStream)
        response.flushBuffer()
    }
}