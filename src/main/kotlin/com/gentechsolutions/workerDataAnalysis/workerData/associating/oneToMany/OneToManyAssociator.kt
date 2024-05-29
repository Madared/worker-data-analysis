package com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToMany

import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData
import com.gentechsolutions.workerDataAnalysis.extensions.into
import com.gentechsolutions.workerDataAnalysis.workerData.comparing.compare
import com.gentechsolutions.workerDataAnalysis.workerData.comparing.WorkerDataComparerType

class OneToManyAssociator(private val comparers: List<WorkerDataComparerType>) {
    fun associate(toAssociate: WorkerData, list: List<WorkerData>): OneToManyAssociation = list
        .filter { comparers.all { comparer -> it.compare(toAssociate, comparer) } }
        .into { OneToManyAssociation(toAssociate, it) }
}

