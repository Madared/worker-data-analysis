package com.gentechsolutions.workerDataAnalysis.workerData.filtering

import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData

data class FilteringRequest(val data: List<WorkerData>, val filter: WorkerDataFilter)