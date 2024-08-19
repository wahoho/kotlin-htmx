package com.wahoho.views.layout

import kotlinx.html.*

fun HTML.layout(e: BODY.() -> Unit) {
    head {
        link(rel = "stylesheet", href = "https://cdn.simplecss.org/simple.min.css")
        link(rel = "stylesheet", href = "/styles.css", type = "text/css")
        script {
            src = "https://unpkg.com/htmx.org@2.0.2"
            integrity = "sha384-Y7hw+L/jvKeWIRRkqWYfPcvVxHzVzn5REgzbawhxAuQGwX1XWe70vji+VSeHOThJ"
            attributes["crossorigin"] = "anonymous"
        }
        script {
            src = "https://unpkg.com/htmx.org@2.0.2/dist/ext/json-enc.js"
        }
    }

    body {
        e()
    }
}