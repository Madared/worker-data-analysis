package com.gentechsolutions.workerDataAnalysis.hoursDifference.reporting

import com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne.OneToOneAssociationResults
import java.io.ByteArrayOutputStream

interface ReportGenerator {
    fun generate(data: OneToOneAssociationResults): ByteArrayOutputStream
}

