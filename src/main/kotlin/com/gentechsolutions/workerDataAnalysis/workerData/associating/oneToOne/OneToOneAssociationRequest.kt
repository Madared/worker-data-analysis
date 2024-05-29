package com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne

import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData
import com.gentechsolutions.workerDataAnalysis.workerData.comparing.WorkerDataComparerType

data class OneToOneAssociationRequest(
    val internal: List<WorkerData>,
    val external: List<WorkerData>,
    val comparers: List<WorkerDataComparerType>
)