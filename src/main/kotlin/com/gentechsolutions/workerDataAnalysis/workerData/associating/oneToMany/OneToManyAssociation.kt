package com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToMany

import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData

data class OneToManyAssociation(val toAssociate: WorkerData, val associated: List<WorkerData>)