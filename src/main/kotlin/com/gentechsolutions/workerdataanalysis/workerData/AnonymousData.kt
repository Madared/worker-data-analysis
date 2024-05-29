package com.gentechsolutions.workerDataAnalysis.workerData

import java.util.*


data class AnonymousData(
    val hours: Float,
    val date: Optional<out Date> = Optional.empty(),
    val lob: Optional<out String> = Optional.empty(),
    val site: Optional<out String> = Optional.empty(),
    val vendor: Optional<out String> = Optional.empty(),
    val language: Optional<out String> = Optional.empty(),
)