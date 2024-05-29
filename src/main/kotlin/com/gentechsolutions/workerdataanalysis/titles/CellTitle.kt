package com.gentechsolutions.workerDataAnalysis.titles

import java.util.*

data class CellTitle(val columnNumber: Int, val title: String)

data class CellTitlesTemplate(
    val id: CellTitle,
    val name: CellTitle,
    val hours: CellTitle,
    val date: Optional<out CellTitle>,
    val lob: Optional<out CellTitle>,
    val site: Optional<out CellTitle>,
    val vendor: Optional<out CellTitle>,
    val language: Optional<out CellTitle>,
)