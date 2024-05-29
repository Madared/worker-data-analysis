package com.gentechsolutions.workerDataAnalysis.hoursDifference.reporting

import com.gentechsolutions.workerDataAnalysis.extensions.orDefault
import com.gentechsolutions.workerDataAnalysis.extensions.orOption
import com.gentechsolutions.workerDataAnalysis.workerData.Worker
import com.gentechsolutions.workerDataAnalysis.workerData.WorkerId
import com.gentechsolutions.workerDataAnalysis.workerData.WorkerIdentifier
import com.gentechsolutions.workerDataAnalysis.workerData.WorkerName
import com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne.OneToOneAssociation
import com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne.OneToOneAssociationResults
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFColor
import java.awt.Color
import java.io.ByteArrayOutputStream
import java.util.*

class ReportGeneratorExcel : ReportGenerator {
    override fun generate(data: OneToOneAssociationResults): ByteArrayOutputStream {
        val workbook = WorkbookFactory.create(true)
        val sheet = workbook.createSheet("analysis")
        val errorSheet = workbook.createSheet("errors")
        val titleRow = sheet.createRow(0)
        val titleStyle = titleCellStyle(workbook)
        setTitles(titleRow, titleStyle)
        val errorTitleRow = errorSheet.createRow(0)
        setErrorTitles(errorTitleRow, titleStyle)
        for (valueWithIndex in data.associated.withIndex()) {
            val row = sheet.createRow(valueWithIndex.index + 1)
            setData(valueWithIndex.value, row)
        }
        for (valueWithIndex in data.errors.withIndex()) {
            val row = errorSheet.createRow(valueWithIndex.index + 1)
            setErrors(row, valueWithIndex.value)
        }
        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()
        return outputStream
    }

    private fun titleCellStyle(workbook: Workbook): CellStyle {
        val cellStyle = workbook.createCellStyle()
        val font = workbook.createFont()
        font.bold = true

        // Set the background color to gray
        val grayColor = XSSFColor(Color(192, 192, 192), null) // Light gray
        cellStyle.setFillForegroundColor(grayColor)
        cellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND
        cellStyle.setFont(font)
        return cellStyle
    }

    private fun createTitleCell(row: Row, column: Int, style: CellStyle, title: String) {
        val cell = row.createCell(column)
        cell.cellStyle = style
        cell.setCellValue(title)
    }

    private fun setTitles(row: Row, style: CellStyle) {
        createTitleCell(row, REPORT_COLUMNS.ID, style, "ID")
        createTitleCell(row, REPORT_COLUMNS.NAME, style, "Name")
        createTitleCell(row, REPORT_COLUMNS.VENDOR, style, "Vendor")
        createTitleCell(row, REPORT_COLUMNS.LOB, style, "Lob")
        createTitleCell(row, REPORT_COLUMNS.SITE, style, "Site")
        createTitleCell(row, REPORT_COLUMNS.LANGUAGE, style, "Language")
        createTitleCell(row, REPORT_COLUMNS.INTERNAL_HOURS, style, "Internal Hours")
        createTitleCell(row, REPORT_COLUMNS.EXTERNAL_HOURS, style, "External Hours")
        createTitleCell(row, REPORT_COLUMNS.HOURS_DIFFERENCE, style, "Difference")
    }

    private fun setErrorTitles(row: Row, style: CellStyle) {
        createTitleCell(row, 0, style, "Error")
    }

    private fun setErrors(row: Row, error: Throwable) {
        row.createCell(0).setCellValue(error.message ?: "Unknown error")
    }

    private fun setData(associated: OneToOneAssociation, row: Row) {
        row.createCell(REPORT_COLUMNS.ID).setCellValue(getIdIfExists(associated.associated.worker))
        row.createCell(REPORT_COLUMNS.NAME).setCellValue(getNameIfExists(associated.associated.worker))
        row.createCell(REPORT_COLUMNS.LANGUAGE)
            .setCellValue(
                associated.associated.workerData.language.orOption(associated.toAssociate.workerData.language)
                    .orDefault("No language")
            )
        row.createCell(REPORT_COLUMNS.VENDOR)
            .setCellValue(
                associated.associated.workerData.vendor.orOption(associated.toAssociate.workerData.vendor)
                    .orDefault("No vendor")
            )
        row.createCell(REPORT_COLUMNS.SITE)
            .setCellValue(
                associated.associated.workerData.site.orOption(associated.toAssociate.workerData.site)
                    .orDefault("No site")
            )
        row.createCell(REPORT_COLUMNS.LOB)
            .setCellValue(
                associated.associated.workerData.lob.orOption(associated.toAssociate.workerData.lob).orDefault("No lob")
            )
        row.createCell(REPORT_COLUMNS.INTERNAL_HOURS).setCellValue(associated.associated.workerData.hours.toDouble())
        row.createCell(REPORT_COLUMNS.EXTERNAL_HOURS).setCellValue(associated.toAssociate.workerData.hours.toDouble())
        row.createCell(REPORT_COLUMNS.HOURS_DIFFERENCE)
            .setCellValue((associated.associated.workerData.hours - associated.toAssociate.workerData.hours).toDouble())
    }

    private fun getIdIfExists(workerId: WorkerIdentifier) = when (workerId) {
        is WorkerId -> workerId.id
        is Worker -> workerId.id
        else -> "no id"
    }

    private fun getNameIfExists(workerId: WorkerIdentifier) = when (workerId) {
        is WorkerName -> workerId.name
        is Worker -> workerId.name
        else -> "no name"
    }
}

