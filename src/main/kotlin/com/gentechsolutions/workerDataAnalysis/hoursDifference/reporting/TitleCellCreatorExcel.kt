package com.gentechsolutions.workerDataAnalysis.hoursDifference.reporting

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFColor
import java.awt.Color

class TitleCellCreatorExcel(private val sheet: Sheet) {
    val id = 0
    val name = 1
    val lob = 2
    val language = 3
    val vendor = 4
    val internalHours = 5
    val externalHours = 6
    val hoursDifference = 7

    fun setTitles(): Unit {
        val style = titleStyle()
        val row = sheet.createRow(0)
        createTitle(row, id, style, "ID")
        createTitle(row, name, style, "Name")
        createTitle(row, language, style, "Language")
        createTitle(row, vendor, style, "Vendor")
        createTitle(row, internalHours, style, "Internal Hours")
        createTitle(row, externalHours, style, "External Hours")
        createTitle(row, hoursDifference, style, "Hours difference")
    }

    private fun createTitle(row: Row, column: Int, style: CellStyle, title: String) {
        val titleCell = row.createCell(0)
        titleCell.cellStyle = style
        titleCell.setCellValue(title)
    }

    private fun titleStyle(): CellStyle {
        val workbook = sheet.workbook
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
}