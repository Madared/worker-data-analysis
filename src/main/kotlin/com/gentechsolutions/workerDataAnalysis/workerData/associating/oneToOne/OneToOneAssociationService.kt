package com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne

import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData
import com.gentechsolutions.workerDataAnalysis.workerData.comparing.WorkerDataComparerType
import org.springframework.stereotype.Service

@Service
class OneToOneAssociationService {
    fun associate(
        internal: List<WorkerData>,
        external: List<WorkerData>,
        comparers: List<WorkerDataComparerType>
    ): OneToOneAssociationResults {
        val associator =
            if (internal.size > 20000 || external.size > 20000 && Runtime.getRuntime().availableProcessors() > 1) {
                OneToOneAssociatorParallel(comparers)
            } else {
                OneToOneAssociatorSequential(comparers)
            }
        val results = associator.associate(internal, external)
        return OneToOneAssociationResults.from(results)
    }
}