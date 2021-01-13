package com.sammidev.structured.service

import com.sammidev.structured.entity.Todolist
import java.util.*

interface Todolistservice {
    fun findAllTodolist(): List<Todolist>
    fun findByTodoName(name: String): Todolist?
    fun addTodolist(todolist: Todolist)
    fun removeTodolist(todolist: Todolist) : Boolean
    fun removeTodolistById(id: UUID) : Boolean
    fun updateTodolist(id: UUID, todolist: Todolist) : Boolean
}