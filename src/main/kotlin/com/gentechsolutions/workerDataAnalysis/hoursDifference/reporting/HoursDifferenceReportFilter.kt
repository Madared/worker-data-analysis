package com.gentechsolutions.workerDataAnalysis.hoursDifference.reporting

import com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne.OneToOneAssociation

class HoursDifferenceReportFilter(
    private val maxDifference: Float?,
    private val minDifference: Float?,

    ) {
    fun filter(data: List<OneToOneAssociation>): List<OneToOneAssociation> = data.filter {
        val difference = it.toAssociate.workerData.hours - it.associated.workerData.hours
        val over = if (minDifference == null) true else difference > minDifference
        val under = if (maxDifference == null) true else difference < maxDifference
        over && under
    }
}