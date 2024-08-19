package com.wahoho.domain

import kotlinx.serialization.Serializable

@Serializable
data class Todo(var id: String, val title: String, val content: String)