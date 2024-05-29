package com.gentechsolutions.workerDataAnalysis.workerData.associating.oneToOne

data class OneToOneAssociationResults(val associated: List<OneToOneAssociation>, val errors: List<Throwable>) {
    companion object {
        fun from(results: List<Result<OneToOneAssociation>>): OneToOneAssociationResults {
            val errors = results.filter { it.isFailure }.map { it.exceptionOrNull()!! }
            val successes = results.filter { it.isSuccess }.map { it.getOrThrow() }
            return OneToOneAssociationResults(successes, errors)
        }
    }
}