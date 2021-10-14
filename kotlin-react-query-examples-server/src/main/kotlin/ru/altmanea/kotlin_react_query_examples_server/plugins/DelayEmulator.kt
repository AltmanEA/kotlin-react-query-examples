package ru.altmanea.kotlin_react_query_examples_server.plugins

import io.ktor.application.*
import io.ktor.util.*
import kotlinx.coroutines.delay

class DelayEmulator(configuration: Configuration) {
    val delay = configuration.delay

    class Configuration {
        var delay = 1000L
    }
    companion object Feature : ApplicationFeature<ApplicationCallPipeline, Configuration, DelayEmulator> {

        override val key = AttributeKey<DelayEmulator>("DelayEmulator")

        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): DelayEmulator {
            val configuration = Configuration().apply(configure)
            val feature = DelayEmulator(configuration)
            pipeline.intercept(ApplicationCallPipeline.Features) {
                delay(feature.delay)
            }
            return feature
        }
    }
}