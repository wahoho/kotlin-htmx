package com.wahoho.views.todo

import com.wahoho.views.layout.layout
import kotlinx.html.*
import java.util.*

fun HTML.listTodos() = layout {
    val uid = UUID.randomUUID()

    h1 { +"todos!!!" }
    div {
        ul {
            id = "list"
            attributes["hx-get"] = "/todos?uid=$uid"
            attributes["hx-trigger"] = "load"

            p(classes = "htmx-indicator") { +"Loading..." }
        }
    }

    form {
        attributes["hx-post"] = "/todos?uid=$uid"
        attributes["hx-ext"] = "json-enc"
        attributes["hx-swap"] = "afterbegin"
        attributes["hx-target"] = "#list"

        input {
            type = InputType.hidden
            name = "id"
            value = uid.toString()
        }

        input {
            type = InputType.text
            name = "title"
            placeholder = "Title"
        }

        input(classes = "pl") {
            type = InputType.text
            name = "content"
            placeholder = "Content"
        }

        button(classes = "pl") {
            type = ButtonType.submit
            +"Create"
        }
    }
}