package com.sammidev

import java.util.*

var model = mutableListOf<String>()
var scanner = Scanner(System.`in`)

fun input(info: String): String? {
    print("$info : ")
    return scanner.nextLine()
}

fun viewShowTodoList() {
    loop@ while (true) {
        showToDoList()

        println("MENU : ")
        println("1. Tambah")
        println("2. Hapus")
        println("x. Keluar")

        val input = input("Pilih")
        when (input) {
            "1" -> viewAddToDoList()
//            "2" -> viewRemoveTodoList()
            "x" -> break@loop
            else -> println("Pilihan tidak dimengerti")
        }
    }
}

fun viewAddToDoList() {
    println("MENAMBAH TODOLIST")
    val todo = input("Todo (x Jika Batal)")

    if (todo == "x" || !todo!!.isNotBlank()) {
    }else addToDoList(todo)
}

fun addToDoList(todo: String?) {
    model.add(todo!!)
}

fun showToDoList() {

}


fun main() {
    viewShowTodoList()
    println(model.toString())
}


