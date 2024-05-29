package com.gentechsolutions.workerDataAnalysis.workerData.parsing.files

import com.gentechsolutions.workerDataAnalysis.titles.TitlesTemplateCreation

data class ExcelFile(
    val bytes: ByteArray,
    val titlesTemplate: TitlesTemplateCreation
) : WorkerDataFile {
    override val type: WorkerDataFileType = WorkerDataFileType.Excel
}