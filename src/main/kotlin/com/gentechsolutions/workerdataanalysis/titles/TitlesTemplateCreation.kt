package com.gentechsolutions.workerDataAnalysis.titles

import com.gentechsolutions.workerDataAnalysis.extensions.toOption
import java.util.*

data class TitlesTemplateCreation(
    val id: String,
    val name: String,
    val hours: String,
    val date: String? = null,
    val site: String? = null,
    val lob: String? = null,
    val vendor: String? = null,
    val language: String? = null
) {

    fun createTitlesTemplate(): Result<TitlesTemplate> {
        val id = Title.create(id);
        val name = Title.create(name);
        val hours = Title.create(hours);
        val date = nullToResult(date)
        val site = nullToResult(site);
        val lob = nullToResult(lob);
        val vendor = nullToResult(vendor);
        val language = nullToResult(language);

        if (id.isFailure) return Result.failure(Exception("Invalid id template"))
        if (name.isFailure) return Result.failure(Exception("Invalid name template"))
        if (hours.isFailure) return Result.failure(Exception("Invalid hours template"))

        val template = TitlesTemplate(
            id.getOrThrow(),
            name.getOrThrow(),
            hours.getOrThrow(),
            date.getOrNull().toOption(),
            site.getOrNull().toOption(),
            lob.getOrNull().toOption(),
            vendor.getOrNull().toOption(),
            language.getOrNull().toOption()
        )
        return Result.success(template);
    }

    private fun nullToResult(title: String?): Result<Title> =
        if (title == null)
            Result.failure(Exception())
        else
            Title.create(title);
}