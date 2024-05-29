package com.gentechsolutions.workerDataAnalysis.workerData.parsing.excel

import com.gentechsolutions.workerDataAnalysis.titles.CellTitlesTemplate
import org.apache.poi.ss.usermodel.Row

interface ExcelTitleParser {
    fun getTitles(row: Row): Result<CellTitlesTemplate>
}

