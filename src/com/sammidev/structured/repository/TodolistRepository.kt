package com.sammidev.structured.repository

import com.sammidev.structured.entity.Todolist
import java.util.*

interface TodolistRepository {
    fun findAll(): List<Todolist>
    fun findByName(nameTodo: String): Todolist?
    fun add(todolist: Todolist)
    fun remove(todolist: Todolist) : Boolean
    fun removeById(id: UUID) : Boolean
    fun update(id: UUID, todolist: Todolist) : Boolean
}