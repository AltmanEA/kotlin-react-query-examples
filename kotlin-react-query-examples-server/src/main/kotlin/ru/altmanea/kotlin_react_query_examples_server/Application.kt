package ru.altmanea.kotlin_react_query_examples_server

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.altmanea.kotlin_react_query_examples_server.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        configureSerialization()
        autoRefetching()
    }.start(wait = true)
}
