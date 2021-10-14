package ru.altmanea.kotlin_react_query_examples_server

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.altmanea.kotlin_react_query_examples_server.auto_refetching.autoRefetching
import ru.altmanea.kotlin_react_query_examples_server.plugins.DelayEmulator

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(CORS){
            anyHost()
        }
        install(ContentNegotiation) {
            json()
        }
        install(DelayEmulator){
            delay = 1000
        }
        autoRefetching()
    }.start(wait = true)
}
