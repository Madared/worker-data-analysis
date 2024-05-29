package com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne

import com.gentechsolutions.workerDataAnalysis.extensions.into
import com.gentechsolutions.workerDataAnalysis.WorkerNotFoundInternal
import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData
import com.gentechsolutions.workerDataAnalysis.workerData.comparing.WorkerDataComparerType
import com.gentechsolutions.workerDataAnalysis.workerData.comparing.compare

class OneToOneAssociatorParallel(private val comparers: List<WorkerDataComparerType>) : OneToOneAssociator {
    override fun associate(internal: List<WorkerData>, external: List<WorkerData>): List<Result<OneToOneAssociation>> {
        val associationResults = internal
            .parallelStream()
            .map { int ->
                external
                    .filter { ext -> comparers.all { comparer -> int.compare(ext, comparer) } }
                    .into { ensureUnique(it, int.worker) }
                    .map { OneToOneAssociation(int, it) }
            }.toList()
        val missingExternals = external
            .parallelStream()
            .map { ext ->
                val found = internal.find { int -> comparers.all { comparer -> ext.compare(int, comparer) } }
                if (found == null) {
                    Result.failure<OneToOneAssociation>(WorkerNotFoundInternal(ext.worker))
                } else {
                    null
                }

            }
            .toList()
            .filterNotNull()
        return associationResults + missingExternals
    }
}