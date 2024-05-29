package com.gentechsolutions.workerDataAnalysis

import com.gentechsolutions.workerDataAnalysis.workerData.AnonymousData
import com.gentechsolutions.workerDataAnalysis.workerData.Worker
import com.gentechsolutions.workerDataAnalysis.workerData.WorkerData
import com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne.OneToOneAssociatorSequential
import com.gentechsolutions.workerDataAnalysis.workerData.comparing.WorkerDataComparerType
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

class OneToOneAssociationTests {
    val first = WorkerData(
        Worker("Id", "Name"),
        AnonymousData(40f)
    )

    val second = WorkerData(
        Worker("Id", "Name"),
        AnonymousData(20f)
    )

    @Test
    fun association_by_worker_gives_1_success() {
        val comparers = listOf(WorkerDataComparerType.Id, WorkerDataComparerType.Name)
        val sequentialAssociator = OneToOneAssociatorSequential(comparers)
        val parallelAssociator = OneToOneAssociatorSequential(comparers)
        val associationSequential = sequentialAssociator.associate(listOf(first), listOf(second))
        val associationParallel = parallelAssociator.associate(listOf(first), listOf(second))
        assertTrue(associationSequential.size == 1)
        assertTrue(associationParallel.size == 1)
        assertTrue(associationSequential.all { it.isSuccess })
        assertTrue(associationParallel.all { it.isSuccess })
    }

    @Test
    fun association_by_missing_fields_gives_1_success() {
        val comparers = listOf(WorkerDataComparerType.Date)
        val sequentialAssociator = OneToOneAssociatorSequential(comparers)
        val parallelAssociator = OneToOneAssociatorSequential(comparers)
        val associationParallel = parallelAssociator.associate(listOf(first), listOf(second))
        val associationSequential = sequentialAssociator.associate(listOf(first), listOf(second))
        assertTrue(associationSequential.size == 1)
        assertTrue(associationParallel.size == 1)
        assertTrue(associationSequential.all { it.isSuccess })
        assertTrue(associationParallel.all { it.isSuccess })
    }

    @Test
    fun association_by_differing_fields_gives_2_error() {
        val comparers = listOf(WorkerDataComparerType.Date)
        val sequentialAssociator = OneToOneAssociatorSequential(comparers)
        val parallelAssociator = OneToOneAssociatorSequential(comparers)
        val alteredFirst = first.copy(workerData = first.workerData.copy(date = Optional.of(Date("10/10/2010"))))
        val alteredSecond = second.copy(workerData = second.workerData.copy(date = Optional.of(Date("11/10/2010"))))
        val associationParallel = parallelAssociator.associate(listOf(alteredFirst), listOf(alteredSecond))
        val associationSequential = sequentialAssociator.associate(listOf(alteredFirst), listOf(alteredSecond))
        assertTrue(associationSequential.size == 2)
        assertTrue(associationSequential.all { it.isFailure })
        assertTrue(associationParallel.size == 2)
        assertTrue(associationParallel.all { it.isFailure })
    }

    @Test
    fun association_by_one_missing_field_on_one_dataset_gives_2_errors() {
        val comparers = listOf(WorkerDataComparerType.Date)
        val sequentialAssociator = OneToOneAssociatorSequential(comparers)
        val parallelAssociator = OneToOneAssociatorSequential(comparers)
        val alteredFirst = first.copy(workerData = first.workerData.copy(date = Optional.of(Date("10/10/2010"))))
        val associationParallel = parallelAssociator.associate(listOf(alteredFirst), listOf(second))
        val associationSequential = sequentialAssociator.associate(listOf(alteredFirst), listOf(second))
        assertTrue(associationSequential.size == 2)
        assertTrue(associationSequential.all { it.isFailure })
        assertTrue(associationParallel.size == 2)
        assertTrue(associationParallel.all { it.isFailure })
    }
}