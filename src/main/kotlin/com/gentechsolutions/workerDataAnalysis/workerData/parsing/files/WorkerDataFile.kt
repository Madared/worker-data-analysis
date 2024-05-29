package com.gentechsolutions.workerDataAnalysis.workerData.parsing.files

sealed interface WorkerDataFile {
    val type: WorkerDataFileType
}

