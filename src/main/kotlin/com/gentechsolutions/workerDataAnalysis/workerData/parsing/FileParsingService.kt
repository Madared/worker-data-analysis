package com.gentechsolutions.workerDataAnalysis.workerData.parsing

import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.excel.ExcelFileParser
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.excel.ExcelTitleTemplateParser
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.files.ExcelFile
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.files.WorkerDataFile
import org.springframework.stereotype.Service

@Service
class FileParsingService {
    fun parse(file: WorkerDataFile): Result<WorkerDataParsingResults> = when (file) {
        is ExcelFile -> {
            val titlesTemplate = file.titlesTemplate.createTitlesTemplate()
            if (titlesTemplate.isFailure) {
                Result.failure(titlesTemplate.exceptionOrNull()!!)
            } else {
                val titlesParser = ExcelTitleTemplateParser(titlesTemplate.getOrThrow())
                val parser = ExcelFileParser(titlesParser)
                val parsed = parser.parse(file.bytes.inputStream())
                parsed.map { toDataSetResults(it) }
            }
        }
    }

    private fun toDataSetResults(parsingResult: List<Result<WorkerData>>): WorkerDataParsingResults {
        val successes = parsingResult.filter { it.isSuccess }.map { it.getOrThrow() }
        val errors = parsingResult.filter { it.isFailure }.map { it.exceptionOrNull()!!.message ?: "Unknown error!" }
        return WorkerDataParsingResults(successes, errors)
    }
}