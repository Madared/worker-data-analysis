package com.gentechsolutions.workerDataAnalysis.titles

class Title private constructor(private val title: String) {
    fun title(): String = title;

    companion object {
        fun create(title: String?): Result<Title> {
            if (title == null) {
                return Result.failure(Exception("Title cannot be null"))
            };
            if (title.isBlank()) {
                return Result.failure(Exception("Title cannot be blank"))
            };
            return Result.success(Title(title));
        }
    }
}