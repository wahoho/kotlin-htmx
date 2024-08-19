package com.wahoho.plugins

import com.wahoho.domain.Todo
import com.wahoho.domain.service.TodoService
import com.wahoho.views.todo.todo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.configureTodoRoutes() {
    todoRoutes(TodoService())
}

fun Application.todoRoutes(todoService: TodoService) {
    routing {
        // Create to-do
        post("/todos") {
            val id = call.request.queryParameters["uid"] ?: throw BadRequestException("no id")
            val todo = call.receive<Todo>().also { todoService.create(it) }

            call.respondHtml {
                body {
                    li {
                        todo(todo, id)
                    }
                }
            }

        }

        get("/todos") {
            val id = call.request.queryParameters["uid"] ?: throw BadRequestException("no id")
            val todos = todoService.readAll()

            call.respondHtml {
                body {
                    for (todoItem in todos.sortedByDescending { it.id }) {
                        li {
                            todo(todoItem, id)
                        }
                    }
                }
            }
        }

        // Read to-do
        get("/todos/{id}") {
            val id = call.parameters["id"] ?: throw BadRequestException("Invalid ID")

            try {
                val todo = todoService.findById(id) ?: throw NotFoundException("Todo not found")
                call.respond(HttpStatusCode.OK, todo)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Show to-do edit form
        get("/todos/{id}/form") {
            val uid = call.request.queryParameters["uid"] ?: throw BadRequestException("no id")
            val id = call.parameters["id"] ?: throw BadRequestException("Invalid ID")

            try {
                val todo = todoService.findById(id) ?: throw NotFoundException("Todo not found")
                call.respondHtml {
                    body {
                        form {
                            attributes["hx-put"] = "/todos/$id?uid=$uid"
                            attributes["hx-ext"] = "json-enc"
                            attributes["hx-target"] = "closest li"
                            attributes["hx-swap"] = "outerHTML"

                            input {
                                type = InputType.hidden
                                name = "id"
                                value = uid
                            }

                            input {
                                type = InputType.text
                                name = "title"
                                value = todo.title
                            }

                            input(classes = "pl") {
                                type = InputType.text
                                name = "content"
                                value = todo.content
                            }

                            button(classes = "pl") {
                                type = ButtonType.submit
                                +"Edit"
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Update to-do
        put("/todos/{id}") {
            val uid = call.request.queryParameters["uid"] ?: throw BadRequestException("no id")
            val id = call.parameters["id"] ?: throw BadRequestException("Invalid ID")
            val todo = call.receive<Todo>().apply { this.id = id }

            todoService.update(id, todo)
            call.respondHtml {
                body {
                    li {
                        todo(todo, uid)
                    }
                }
            }
        }

        // Delete to-do
        delete("/todos/{id}") {
            val id = call.parameters["id"] ?: throw BadRequestException("Invalid ID")

            todoService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun BODY.li(e: LI.() -> Unit) = LI(
    initialAttributes = mutableMapOf(),
    consumer = consumer
).visit(e)