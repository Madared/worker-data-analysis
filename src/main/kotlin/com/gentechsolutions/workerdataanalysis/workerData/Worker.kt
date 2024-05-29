package com.gentechsolutions.workerDataAnalysis.workerData

sealed interface WorkerIdentifier;
data class WorkerId(val id: String) : WorkerIdentifier
data class WorkerName(val name: String) : WorkerIdentifier
data class Worker(val id: String, val name: String) : WorkerIdentifier