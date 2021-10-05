package ru.altmanea.kotlin_react_query_examples_server

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

private val list = arrayListOf("Item1", "Item2", "Item3")
private const val path = "ar"

fun Application.autoRefetching() {
    routing {
        get(path) {
            call.respond(list)

        }
        get("$path/add/{value}") {
            call.parameters["value"]?.let {
                list.add(it)
            }
            call.respond(list)
        }
        get("$path/clear") {
            list.clear()
            call.respond(list)
        }
    }
}
