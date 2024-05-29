package com.gentechsolutions.workerDataAnalysis.hoursDifference

import com.gentechsolutions.workerDataAnalysis.hoursDifference.reporting.HoursDifferenceReportFilter
import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData
import com.gentechsolutions.workerDataAnalysis.workerData.WorkerDataFilter
import com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne.OneToOneAssociator
import com.gentechsolutions.workerDataAnalysis.workerData.comparing.WorkerDataComparerType

data class HoursDifferenceRequest(
    val internal: List<WorkerData>,
    val external: List<WorkerData>,
    val dataFilter: WorkerDataFilter?,
    val comparers: List<WorkerDataComparerType>,
    val reportingFilter: HoursDifferenceReportFilter?
)