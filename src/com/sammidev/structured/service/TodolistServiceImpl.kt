package com.sammidev.structured.service

import com.sammidev.structured.entity.Todolist
import com.sammidev.structured.repository.TodolistRepository
import com.sammidev.structured.validator.TodolistValidator
import java.util.*

class TodolistServiceImpl(val repository: TodolistRepository) : Todolistservice{

    override fun findAllTodolist(): List<Todolist> {
        return repository.findAll()
    }

    override fun findByTodoName(name: String): Todolist? {
        return repository.findByName(name)
    }

    override fun addTodolist(todolist: Todolist) {

        val validation = TodolistValidator().validate(todolist)
        if (validation) {
            if (todolist.id == null) {
                todolist.id = UUID.randomUUID()
                repository.add(todolist)
            }
        }
    }

    override fun removeTodolist(todolist: Todolist): Boolean {
        return repository.remove(todolist)
    }

    override fun removeTodolistById(id: UUID): Boolean {
        return repository.removeById(id)
    }

    override fun updateTodolist(id: UUID, todolist: Todolist): Boolean {
        val validation = TodolistValidator().validate(todolist)
        if (validation) {
            repository.update(id, todolist)
            return true
        }
        return false
    }
}