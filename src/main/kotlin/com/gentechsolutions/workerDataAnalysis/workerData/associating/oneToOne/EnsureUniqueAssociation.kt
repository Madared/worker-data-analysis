package com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne

import com.gentechsolutions.workerDataAnalysis.MoreThanOneMatchForUniqueAssociation
import com.gentechsolutions.workerDataAnalysis.WorkerNotFoundExternal
import com.gentechsolutions.workerDataAnalysis.workerData.Worker
import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData

fun ensureUnique(list: List<WorkerData>, worker: Worker): Result<WorkerData> = when (list.size) {
    0 -> Result.failure(WorkerNotFoundExternal(worker))
    1 -> Result.success(list[0])
    else -> Result.failure(MoreThanOneMatchForUniqueAssociation(worker))
}