package com.gentechsolutions.workerDataAnalysis

import com.gentechsolutions.workerDataAnalysis.workerData.Worker
import org.springframework.http.ResponseEntity

class InvalidAssociation() : Exception("Something went wrong associating data sets.")
class AggregateException(exceptions: List<Throwable>) : Exception(
    exceptions.map { it.message }.joinToString(",\n")
)
class WorkerNotFoundInternal(worker: Worker) : Throwable("$worker not found in internal file");
class WorkerNotFoundExternal(worker: Worker) : Throwable("$worker not found in external file");
class MoreThanOneMatchForUniqueAssociation(worker: Worker) :
    Throwable("Worker: $worker had multiple matches in unique association!")

abstract class HttpException(message: String) : Exception(message) {
    abstract fun <T> mapToResponseEntity(data: T): ResponseEntity<T>
}