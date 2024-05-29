package com.gentechsolutions.workerDataAnalysis.workerData.parsing

import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData

data class WorkerDataParsingResults(val sets: List<WorkerData>, val errors: List<String>)