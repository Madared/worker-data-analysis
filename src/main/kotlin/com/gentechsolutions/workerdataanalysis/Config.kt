package com.gentechsolutions.workerDataAnalysis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.gentechsolutions.workerDataAnalysis.workerData.aggregating.Aggregator
import com.gentechsolutions.workerDataAnalysis.workerData.aggregating.AggregatorSerializer
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.files.WorkerDataFile
import com.gentechsolutions.workerDataAnalysis.workerData.parsing.ParsableFileDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {

    @Bean
    fun myModule(): SimpleModule {
        val module = SimpleModule()
        module.addDeserializer(WorkerDataFile::class.java, ParsableFileDeserializer())
        module.addDeserializer(Aggregator::class.java, AggregatorSerializer())
        return module
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper().apply {
            findAndRegisterModules()
        }
        mapper.registerModule(myModule())
        return mapper
    }
}