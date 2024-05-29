package com.gentechsolutions.workerDataAnalysis.workerData.comparing

import com.gentechsolutions.workerDataAnalysis.extensions.compareWith
import com.gentechsolutions.workerDataAnalysis.extensions.normalize
import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData

fun WorkerData.compare(other: WorkerData, type: WorkerDataComparerType) = when (type) {
    WorkerDataComparerType.Id -> this.worker.id == other.worker.id
    WorkerDataComparerType.Name -> this.worker.name.normalize() == other.worker.name.normalize()
    WorkerDataComparerType.Date -> this.workerData.date == other.workerData.date
    WorkerDataComparerType.Lob -> this.workerData.lob.compareWith(other.workerData.lob) { one, two -> one.normalize() == two.normalize() }
    WorkerDataComparerType.Vendor -> this.workerData.vendor.compareWith(other.workerData.vendor) { one, two -> one.normalize() == two.normalize() }
    WorkerDataComparerType.Language -> this.workerData.language.compareWith(other.workerData.language) { one, two -> one.normalize() == two.normalize() }
    WorkerDataComparerType.Site -> this.workerData.site.compareWith(other.workerData.site) { one, two -> one.normalize() == two.normalize() }
}