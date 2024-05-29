package com.gentechsolutions.workerDataAnalysis.workerData.parsing

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.files.ExcelFile
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.files.WorkerDataFile
import com.gentechsolutions.workerDataAnalysis.titles.TitlesTemplateCreation
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.files.WorkerDataFileType

class ParsableFileDeserializer : JsonDeserializer<WorkerDataFile>() {
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): WorkerDataFile {
        val objectMapper = jsonParser.codec as ObjectMapper
        val jsonNode = objectMapper.readTree<JsonNode>(jsonParser)
        val type = jsonNode.get("type").asText()
        val enumType = try {
            WorkerDataFileType.valueOf(type)
        } catch (e: Exception) {
            null
        }

        when (enumType) {
            WorkerDataFileType.Excel -> {
                val bytes = jsonNode.get("bytes").asText().toByteArray()
                val titlesTemplate =
                    objectMapper.treeToValue(jsonNode.get("titlesTemplate"), TitlesTemplateCreation::class.java)
                return ExcelFile(bytes = bytes, titlesTemplate = titlesTemplate)
            }

            else -> throw Exception("Unsupported type")
        }
    }
}