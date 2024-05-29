package com.gentechsolutions.workerDataAnalysis.workerData.parsing.excel

import com.gentechsolutions.workerDataAnalysis.extensions.bind
import com.gentechsolutions.workerDataAnalysis.extensions.normalize
import com.gentechsolutions.workerDataAnalysis.extensions.toOption
import com.gentechsolutions.workerDataAnalysis.titles.CellTitle
import com.gentechsolutions.workerDataAnalysis.titles.CellTitlesTemplate
import com.gentechsolutions.workerDataAnalysis.titles.Title
import com.gentechsolutions.workerDataAnalysis.titles.TitlesTemplate
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import java.util.*
import kotlin.jvm.optionals.getOrNull

class ExcelTitleTemplateParser(private val titlesTemplate: TitlesTemplate): ExcelTitleParser {
    override fun getTitles(row: Row): Result<CellTitlesTemplate> {
        val id = getMandatoryTitle(row, titlesTemplate.id)
        val name = getMandatoryTitle(row, titlesTemplate.name)
        val hours = getMandatoryTitle(row, titlesTemplate.hours)
        val date = getOptionalTitleCell(row, titlesTemplate.date)
        val lob = getOptionalTitleCell(row, titlesTemplate.lob)
        val site = getOptionalTitleCell(row, titlesTemplate.site)
        val vendor = getOptionalTitleCell(row, titlesTemplate.vendor)
        val language = getOptionalTitleCell(row, titlesTemplate.language)

        if (id == null || name == null || hours == null) {
            return Result.failure(Exception("Mandatory fields missing"))
        }
        val cellTemplate = CellTitlesTemplate(
            CellTitle(id.columnIndex, titlesTemplate.id.title()),
            CellTitle(name.columnIndex, titlesTemplate.name.title()),
            CellTitle(hours.columnIndex, titlesTemplate.hours.title()),
            optionalTitleMap(titlesTemplate.date, date),
            optionalTitleMap(titlesTemplate.lob, lob),
            optionalTitleMap(titlesTemplate.site, site),
            optionalTitleMap(titlesTemplate.vendor, vendor),
            optionalTitleMap(titlesTemplate.language, language),
        )
        return Result.success(cellTemplate)
    }

    private fun getMandatoryTitle(row: Row, title: Title): Cell? {
        return row.find { it.stringCellValue.normalize() == title.title().normalize() }
    }

    private fun getOptionalTitleCell(row: Row, title: Optional<out Title>): Optional<out Cell> = row.find {
        it.stringCellValue.normalize() == (title.getOrNull()?.title() ?: "").normalize()
    }.toOption()

    private fun optionalTitleMap(
        title: Optional<out Title>,
        optionalCell: Optional<out Cell>
    ): Optional<out CellTitle> = title.bind {
        optionalCell
            .bind { Optional.ofNullable(it) }
            .map { cell -> CellTitle(cell.columnIndex, it.title()) }
    }
}