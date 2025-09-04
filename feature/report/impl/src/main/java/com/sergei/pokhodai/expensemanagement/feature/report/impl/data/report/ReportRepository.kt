package com.sergei.pokhodai.expensemanagement.feature.report.impl.data.report

import android.content.Context
import com.sergei.pokhodai.expensemanagement.feature.report.api.model.ReportListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

internal class ReportRepository(
    private val context: Context,
) {
    suspend fun getReportsToExcelFile(
        expenseReport: ReportListModel,
        incomeReport: ReportListModel,
        fileName: String
    ): File {
        return withContext(Dispatchers.IO) {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet(SHEET_NAME)

            val headerStyle = workbook.createCellStyle().apply {
                fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
                fillPattern = FillPatternType.SOLID_FOREGROUND
            }

            val headers = arrayOf(
                HEADER_TYPE,
                HEADER_NAME,
                HEADER_DATE,
                HEADER_AMOUNT,
                HEADER_DESCRIPTION,
            )

            val headerRow = sheet.createRow(0)
            headers.forEachIndexed { index, header ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(header)
                cell.cellStyle = headerStyle
            }

            expenseReport.getTable(sheet, 0)
            incomeReport.getTable(sheet, expenseReport.reports.size + 1 + 1)

            val file = File(context.getExternalFilesDir(null), getFileName(fileName))
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            workbook.close()

            file
        }
    }

    private fun ReportListModel.getTable(sheet: XSSFSheet, rowCount: Int) {
        this.reports.forEachIndexed { rowIndex, report ->
            val row = sheet.createRow(rowCount + rowIndex + 1)
            row.createCell(0).setCellValue(report.type.lowercase())
            row.createCell(1).setCellValue(report.name)
            row.createCell(2).setCellValue(report.date)
            row.createCell(3).setCellValue(report.amount)
            row.createCell(4).setCellValue(report.description)
        }
        val expenseReportLastRow = sheet.createRow(rowCount + this.reports.size + 1)
        expenseReportLastRow.createCell(0).setCellValue("Sum")
        expenseReportLastRow.createCell(3).setCellValue(this.sum)
    }

    private companion object {
        const val HEADER_TYPE = "Type"
        const val HEADER_NAME = "Name"
        const val HEADER_DATE = "Date"
        const val HEADER_AMOUNT = "Amount"
        const val HEADER_DESCRIPTION = "Description"
        const val SHEET_NAME = "FinCatReports"

        fun getFileName(name: String): String {
            return "$name.xlsx"
        }
    }
}