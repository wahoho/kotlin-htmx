package com.wahoho.domain.service

import com.wahoho.domain.Todo

private val _database = mutableMapOf<String, Todo>()

class TodoService() {

    // Create new to-do item
    fun create(todo: Todo): String {
       _database[todo.id] = todo
       return todo.id
    }

    fun readAll(): List<Todo> {
       return _database.values.toList()
    }

    // Read a to-do
    fun findById(id: String): Todo? {
       return _database[id]
    }

    // Update a to-do
    fun update(id: String, todo: Todo) {
       _database[id] = todo
    }

    // Delete a to-do
    fun delete(id: String) {
       _database.remove(id)
    }
}