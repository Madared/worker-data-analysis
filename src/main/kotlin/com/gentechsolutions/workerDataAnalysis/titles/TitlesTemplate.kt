package com.gentechsolutions.workerDataAnalysis.titles

import java.util.*

data class TitlesTemplate(
    val id: Title,
    val name: Title,
    val hours: Title,
    val date: Optional<out Title>,
    val lob: Optional<out Title>,
    val site: Optional<out Title>,
    val vendor: Optional<out Title>,
    val language: Optional<out Title>,
)