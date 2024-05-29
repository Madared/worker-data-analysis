package com.gentechsolutions.workerDataAnalysis

import com.gentechsolutions.workerDataAnalysis.hoursDifference.reporting.HoursDifferenceReportFilter
import com.gentechsolutions.workerDataAnalysis.hoursDifference.reporting.ReportGeneratorExcel
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.files.ExcelFile
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.FileParsingService
import com.gentechsolutions.workerDataAnalysis.titles.TitlesTemplateCreation
import com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne.OneToOneAssociationService
import com.gentechsolutions.workerDataAnalysis.workerData.comparing.WorkerDataComparerType
import jakarta.servlet.http.HttpServletResponse
import org.apache.poi.util.IOUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

const val fifteenMinutes = 1 / 60 * 15f

@RestController
@RequestMapping("/excel-excel")
class ExcelToExcelController(
    private val parsingService: FileParsingService,
    private val associationService: OneToOneAssociationService,
) {
    @PostMapping("/generate-report")
    fun getFile(
        @RequestParam("first") first: MultipartFile,
        @RequestParam("second") second: MultipartFile,
        response: HttpServletResponse
    ) {
        val reportGenerator = ReportGeneratorExcel()
        val baseTitles =
            TitlesTemplateCreation(
                "ID",
                "Agent Name",
                "Hours",
                vendor = "Vendor",
                lob = "Lob",
                date = "Date",
                language = "Language",
                site = "Site",
            )
        val firstParsed = parsingService.parse(ExcelFile(first.bytes, baseTitles))
        val secondParsed = parsingService.parse(ExcelFile(second.bytes, baseTitles))
        if (firstParsed.isFailure || secondParsed.isFailure) {
            val exception = firstParsed.exceptionOrNull() ?: secondParsed.exceptionOrNull()!!
            throw exception
        }
        val comparers = listOf(
            WorkerDataComparerType.Id,
            WorkerDataComparerType.Name,
            WorkerDataComparerType.Date
        )
        val associationResults = associationService.associate(firstParsed.getOrThrow().sets, secondParsed.getOrThrow().sets, comparers)
        val filter = HoursDifferenceReportFilter(null, fifteenMinutes)
        val filtered = filter.filter(associationResults.associated)
        val report = reportGenerator.generate(associationResults.copy(associated = filtered))
        response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        val inputStream = ByteArrayInputStream(report.toByteArray())
        IOUtils.copy(inputStream, response.outputStream)
        response.flushBuffer()
    }
}