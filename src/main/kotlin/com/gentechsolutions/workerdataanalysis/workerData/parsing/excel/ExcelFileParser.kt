package com.gentechsolutions.workerDataAnalysis.workerData.parsing.excel

import com.gentechsolutions.workerDataAnalysis.AggregateException
import com.gentechsolutions.workerDataAnalysis.extensions.bind
import com.gentechsolutions.workerDataAnalysis.extensions.toOption
import com.gentechsolutions.workerDataAnalysis.titles.CellTitlesTemplate
import com.gentechsolutions.workerDataAnalysis.workerData.AnonymousData
import com.gentechsolutions.workerDataAnalysis.workerData.Worker
import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream


class ExcelFileParser(
    private val titleParser: ExcelTitleParser,
) {
    fun parse(file: InputStream): Result<List<Result<WorkerData>>> {
        try {
            val workbook = WorkbookFactory.create(file);
            val resultList = mutableListOf<Result<WorkerData>>();
            workbook.forEach { sheet ->
                val result = parseSheet(sheet);
                if (result.isFailure) {
                    return Result.failure(result.exceptionOrNull()!!)
                }
                resultList.addAll(result.getOrThrow())
            }
            return Result.success(resultList);
        } catch (e: Throwable) {
            return Result.failure(e);
        }
    }

    private fun parseSheet(sheet: Sheet): Result<List<Result<WorkerData>>> {
        val results = mutableListOf<Result<WorkerData>>();
        val rows = sheet.toMutableList()
        val firstRow = rows.removeFirst()
        val cellTitles = titleParser.getTitles(firstRow)
        if (cellTitles.isFailure) {
            return Result.failure(cellTitles.exceptionOrNull()!!);
        }
        rows.forEach {
            val result = parseRow(it, cellTitles.getOrThrow())
            results.add(result);
        }
        return Result.success(results);
    }

    private fun parseRow(row: Row, titles: CellTitlesTemplate): Result<WorkerData> {
        val id = row.getCell(titles.id.columnNumber)?.numericCellValue?.toInt()?.toString()
        val name = row.getCell(titles.name.columnNumber)?.stringCellValue
        val hours = row.getCell(titles.hours.columnNumber)?.numericCellValue?.toFloat()
        val date = titles.date.bind { row.getCell(it.columnNumber)?.dateCellValue.toOption() }
        val lob = titles.lob.bind { row.getCell(it.columnNumber)?.stringCellValue.toOption() }
        val site = titles.site.bind { row.getCell(it.columnNumber)?.stringCellValue.toOption() }
        val vendor = titles.vendor.bind { row.getCell(it.columnNumber)?.stringCellValue.toOption() }
        val language = titles.language.bind { row.getCell(it.columnNumber)?.stringCellValue.toOption() }

        val mandatoryTitles = validateMandatory(id, name, hours, row);
        return mandatoryTitles.map {
            WorkerData(
                Worker(id!!, name!!),
                AnonymousData(
                    hours!!,
                    date,
                    lob,
                    site,
                    vendor,
                    language
                )
            );
        }
    }

    private fun validateMandatory(id: String?, name: String?, hours: Float?, row: Row): Result<Unit> {
        val errors = mutableListOf<Throwable>()
        if (id == null) {
            errors.add(Exception("Id not present on row ${row.rowNum}"))
        }
        if (name == null) {
            errors.add(Exception("Name not present on row ${row.rowNum}"))
        }

        if (hours == null) {
            errors.add(Exception("Hours not present on row ${row.rowNum}"))
        }

        if (errors.isNotEmpty()) {
            return Result.failure(AggregateException(errors))
        }
        return Result.success(Unit)
    }
}

