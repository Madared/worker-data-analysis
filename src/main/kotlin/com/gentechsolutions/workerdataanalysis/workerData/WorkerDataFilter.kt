package com.gentechsolutions.workerDataAnalysis.workerData

import com.gentechsolutions.workerDataAnalysis.extensions.normalize
import java.util.*
import kotlin.jvm.optionals.getOrNull

class WorkerDataFilter(
    private val languages: List<String>?,
    private val lobs: List<String>?,
    private val sites: List<String>?,
    private val names: List<String>?,
    private val ids: List<String>?,
    private val dates: List<Date>?,
) {
    fun filter(data: List<WorkerData>): List<WorkerData> = data
        .filter {
            languages?.contains(it.workerData.language.getOrNull()?.normalize()) ?: true
                    && lobs?.contains(it.workerData.lob.getOrNull()?.normalize()) ?: true
                    && sites?.contains(it.workerData.site.getOrNull()?.normalize()) ?: true
                    && names?.contains(it.worker.name.normalize()) ?: true
                    && ids?.contains(it.worker.id.normalize()) ?: true
                    && dates?.contains(it.workerData.date.getOrNull()) ?: true
        }
}