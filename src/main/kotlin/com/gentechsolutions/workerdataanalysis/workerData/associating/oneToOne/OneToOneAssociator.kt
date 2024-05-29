package com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne

import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData

sealed interface OneToOneAssociator {
    fun associate(internal: List<WorkerData>, external: List<WorkerData>): List<Result<OneToOneAssociation>>
}

