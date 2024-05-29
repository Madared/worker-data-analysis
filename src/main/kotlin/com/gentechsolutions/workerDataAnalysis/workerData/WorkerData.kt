package com.gentechsolutions.workerDataAnalysis.workerData

import com.gentechsolutions.workerDataAnalysis.extensions.compareWith
import com.gentechsolutions.workerDataAnalysis.extensions.normalize
import com.gentechsolutions.workerDataAnalysis.workerData.comparing.WorkerDataComparerType

data class WorkerData(
    val worker: Worker,
    val workerData: AnonymousData
)