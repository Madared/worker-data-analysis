package com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne

import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData

data class OneToOneAssociation(val toAssociate: WorkerData, val associated: WorkerData)