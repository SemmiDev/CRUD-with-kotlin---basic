package com.sammidev.structured.validator

import com.sammidev.structured.entity.Todolist

class TodolistValidator {

    fun validate(todolist: Todolist): Boolean {

        val todonotBlank = todolist.todo.isNotBlank()
        val todolength = todolist.todo.length < 30

        return todonotBlank && todolength
    }
}