package com.sammidev.structured.repository

import com.sammidev.structured.entity.Todolist
import java.util.*

class TodolistRepositryImpl : TodolistRepository {

    private var todoListData = mutableListOf<Todolist>()

    override fun findAll(): List<Todolist> = todoListData

    override fun findByName(nameTodo: String): Todolist? {
        todoListData.map {
            if (it.todo == nameTodo) {
                return it
            }
        }
        return null
    }

    override fun add(todolist: Todolist) {
        todoListData.add(todolist)
    }


    override fun remove(todolist: Todolist): Boolean {
        todoListData.map {
            if (it == todolist) {
                todoListData.remove(todolist)
                return true;
            }
        }
        return false;
    }

    override fun removeById(id: UUID): Boolean {
        todoListData.map {
            if (it.id == id) {
                remove(it)
                return true
            }
        }
        return false
    }

    override fun update(id: UUID, todolist: Todolist): Boolean {
        todoListData.map {
            if(it.id == id) {
                it.todo = todolist.todo
                it.start = todolist.start
                it.end = todolist.start
                return true
            }
        }
        return false
    }
}