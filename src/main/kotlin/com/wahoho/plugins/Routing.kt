package com.wahoho.plugins

import com.wahoho.views.todo.listTodos
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondHtml {
                listTodos()
            }
        }
    }
}