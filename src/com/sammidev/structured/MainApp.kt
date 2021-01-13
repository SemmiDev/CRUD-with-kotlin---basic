package com.sammidev.structured

import com.sammidev.structured.entity.Todolist
import com.sammidev.structured.repository.TodolistRepository
import com.sammidev.structured.repository.TodolistRepositryImpl
import com.sammidev.structured.service.TodolistServiceImpl
import com.sammidev.structured.service.Todolistservice
import java.time.LocalDateTime

class MainApp

fun main() {

    val repo: TodolistRepository = TodolistRepositryImpl()
    val service: Todolistservice = TodolistServiceImpl(repo)

    val todo = Todolist(
            id = null,
            todo = "belajar",
            start = LocalDateTime.now(),
            end = LocalDateTime.now().plusDays(2),
            desc = "Belajar Kotlin"
    )

    val todoUpdate = Todolist(
            id = null,
            todo = "belajar update",
            start = LocalDateTime.now(),
            end = LocalDateTime.now().plusDays(2),
            desc = "Belajar Kotlin"
    )

    // adding
    service.addTodolist(todo)
    println("-----------------------------")

    // find by name
    val todoo = service.findByTodoName(todo.todo)
    println(todoo)
    println("-----------------------------")

    // find all
    println(service.findAllTodolist())
    println("-----------------------------")

    // update todo list
    println(service.updateTodolist(todoo!!.id!!,todoUpdate))
    println("-----------------------------")

    // find all
    println(service.findAllTodolist())
    println("-----------------------------")

    // remove todo list by id
    println(service.removeTodolistById(todoo!!.id!!))
    println("-----------------------------")

    // adding
    service.addTodolist(Todolist(
            id = null,
            todo = "belajar",
            start = LocalDateTime.now(),
            end = LocalDateTime.now().plusDays(2),
            desc = "Belajar Kotlin"
    ))
    println("-----------------------------")

    val todooo = service.findByTodoName("belajar")

    // remove todo list
    println(service.removeTodolist(todooo!!))
    println("-----------------------------")

}