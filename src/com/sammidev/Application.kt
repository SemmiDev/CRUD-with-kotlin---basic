package com.sammidev

import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

val taskDB = mutableListOf<TaskModel>()

data class TaskModel(val id: String, var taskName: String, var timeStart: String, var timeEnd: String, var duration: String, var done: Boolean)
data class TaskCreateRequest (val taskName: String, val timeStart: String, val timeEnd: String)
data class TaskUpdateRequest (val taskName: String, val timeStart: String, val timeEnd: String)
data class TaskReadAllRequest (
        val totalTask: Int,
        val tasks : List<TaskModel>
)

interface TaskService {
    fun createTask(taskCreateRequest: TaskCreateRequest): Boolean
    fun readAllTask(): List<TaskModel>
    fun updateTask(id: String, taskUpdateRequest: TaskUpdateRequest): Boolean
    fun deleteTask(id: String): Boolean
    fun findTaskById(id: String): TaskModel?
    fun findTaskByName(name: String): TaskModel?
    fun findTaskByDuration(duration: Int): TaskModel?
    fun findTaskByNearestTime(size: Int): MutableList<TaskModel>?
    fun updateDoneStatus(id: String, statusUpdate: Boolean): Boolean
}

class Helper {
    fun duration(request: String): String {
        val timeEndHour = request.split("-")[1].split(":")[0].toInt()
        val timeEndMinute = request.split("-")[1].split(":")[1].toInt()
        val timeStartHour = request.split("-")[0].split(":")[0].toInt()
        val timeStartMinute = request.split("-")[0].split(":")[1].toInt()

        val timeBegin = (timeEndHour * 60) + timeEndMinute
        val timeEnd = (timeStartHour * 60) + timeStartMinute
        val durationMinutes = timeEnd - timeBegin

        val hourDurationResult = durationMinutes / 60
        val minutesDurationResult =  durationMinutes - (hourDurationResult * 60)

        return "$hourDurationResult:$minutesDurationResult"
    }
    fun quickSortDuration(items: MutableList<Int>): MutableList<Int>{
        if (items.count() < 2) {
            return items
        }
        val pivot = items[items.count() / 2]
        val equal = items.filter { it == pivot }
        val less = items.filter { it < pivot }
        val greater = items.filter { it > pivot } as MutableList<Int>
        return (quickSortDuration(less as MutableList<Int>) + equal + quickSortDuration(greater)) as MutableList<Int>
    }
}
class TaskServiceImplementation : TaskService {
    override fun createTask(taskCreateRequest: TaskCreateRequest): Boolean {

        val id = UUID.randomUUID().toString()
        val time = taskCreateRequest.timeEnd + "-" + taskCreateRequest.timeStart
        val duration = Helper().duration(time)
        val task = TaskModel(
                id,
                taskCreateRequest.taskName,
                taskCreateRequest.timeStart,
                taskCreateRequest.timeEnd,
                duration,
                false
        )

        taskDB.add(task)
        return true
    }
    override fun readAllTask(): List<TaskModel> = taskDB
    override fun updateTask(id: String, taskUpdateRequest: TaskUpdateRequest): Boolean {
        var model: TaskModel?
        val time = taskUpdateRequest.timeEnd + "-" + taskUpdateRequest.timeStart
        val duration = Helper().duration(time)
        taskDB.map {
            if (it.id == id) {
                it.taskName = taskUpdateRequest.taskName
                it.timeStart = taskUpdateRequest.timeStart
                it.timeEnd = taskUpdateRequest.timeEnd
                return true
            }
        }
        return false
    }
    override fun deleteTask(id: String): Boolean {
        val found = findTaskById(id)
        if (found != null) {
            taskDB.remove(found)
            return true
        }
        return false
    }
    override fun findTaskById(id: String): TaskModel? {
        taskDB.map {
            if (it.id == id) {
                return it
            }
        }
        return null
    }
    override fun findTaskByName(name: String): TaskModel? {
        taskDB.map {
            if (it.taskName == name) {
                return it
            }
        }
        return null
    }
    override fun findTaskByDuration(duration: Int): TaskModel? {
        var totalDurationPerMinutes: Int?
        taskDB.map {
            if (((it.duration.split(":")[0].toInt() * 60) + it.duration.split(":")[1].toInt()) == duration) {
                return it
            }
        }
        return null
    }
    override fun findTaskByNearestTime(size: Int): MutableList<TaskModel>? {
        require(size < taskDB.size)
        var durationsList: MutableList<Int> = mutableListOf()

        for (i in 0 until taskDB.size) {
            val result = (taskDB[i].duration.split(":")[0].toInt() * 60) + taskDB[i].duration.split(":")[1].toInt()
            durationsList.add(result)
        }

        var sortDuration: MutableList<Int> =  Helper().quickSortDuration(durationsList)
        var listResult: MutableList<TaskModel> = mutableListOf()
        for (i in 0 until size) {
            val temp = findTaskByDuration(sortDuration[i])
            listResult.add(temp!!)
        }
        return listResult
    }
    override fun updateDoneStatus(id: String, statusUpdate: Boolean): Boolean {
        val found = findTaskById(id)
        if (found != null) {
            taskDB.map {
                if (it.taskName == found.taskName) {
                    it.done = statusUpdate
                    return true
                }
            }
        }
        return false
    }
}
class TaskServiceImplementationTest {
    val taskServiceImplementation = TaskServiceImplementation()

    fun createTaskTestSuccess() {

        val a = TaskCreateRequest("belajar1","20:00","23:00")
        val b = TaskCreateRequest("belajar2","21:00","23:00")
        val c = TaskCreateRequest("belajar3","22:00","23:00")

        taskServiceImplementation.createTask(a)
        taskServiceImplementation.createTask(b)
        taskServiceImplementation.createTask(c)

        assertTrue(taskDB.size == 3)
    }
    fun readAllTaskTestSuccess() {
        val a = taskServiceImplementation.readAllTask()
        assertTrue(a.size == 3)
    }
    fun findTaskByIdTestSuccess() {

        taskDB.add(TaskModel("testid","ngoding","20:00","12:00","23",true))
        val a = taskServiceImplementation.findTaskById("testid")
        if (a != null) {
            assertEquals("ngoding", a.taskName)
        }
    }
    fun findTaskByNameTestSuccess() {
        taskDB.add(TaskModel("testid","ngoding","20:00","12:00","23",true))
        val a = taskServiceImplementation.findTaskByName("ngoding")
        if (a != null) {
            assertEquals(true, a.done)
        }
    }
    fun findTaskByDurationTestSuccess() {
        val a = TaskCreateRequest("belajar1","20:00","23:00")
        val result = TaskServiceImplementation().findTaskByName("belajar1")

        val test = taskServiceImplementation.findTaskByDuration(180)
        println(test)
    }
    fun findTaskByNearestTimeTestSuccess() {
        val a = TaskCreateRequest("belajar1","20:00","23:00")
        val b = TaskCreateRequest("belajar2","21:00","23:00")
        val c = TaskCreateRequest("belajar3","22:00","23:00")
        taskServiceImplementation.createTask(a)
        taskServiceImplementation.createTask(b)
        taskServiceImplementation.createTask(c)
        val result = taskServiceImplementation.findTaskByNearestTime(2)
        assertTrue((result!![0].duration.split(":")[0].toInt() * 60) == 60)
    }
    fun deleteTaskTestSuccess() {
        val a = TaskCreateRequest("belajar1","20:00","23:00")
        val result = TaskServiceImplementation().findTaskByName("belajar1")
        val test = taskServiceImplementation.deleteTask(result!!.id)
        assertTrue(test)
    }
    fun updateDoneStatusTestSuccess() {
        val b = TaskCreateRequest("belajar1","20:00","23:00")
        taskServiceImplementation.createTask(b)
        val result = TaskServiceImplementation().findTaskByName("belajar1")
        val test = taskServiceImplementation.updateDoneStatus(result!!.id,true)
        assertFalse(!test)
    }
    fun updateTaskTestSuccess() {
        val c = TaskCreateRequest("belajar3","22:00","23:00")
        taskServiceImplementation.createTask(c)
        val result = TaskServiceImplementation().findTaskByName("belajar3")

        taskServiceImplementation.updateTask(result!!.id, TaskUpdateRequest(
                "UPDATE","20:00","24:00"
        ))

        val testFindByName = taskServiceImplementation.findTaskByName("UPDATE")
        println(testFindByName)
        assertEquals("UPDATE", result.taskName)
    }

    fun runAllTests() {
        createTaskTestSuccess()
        readAllTaskTestSuccess()
        findTaskByIdTestSuccess()
        findTaskByNameTestSuccess()
        findTaskByDurationTestSuccess()
        findTaskByNearestTimeTestSuccess()
        updateDoneStatusTestSuccess()
        deleteTaskTestSuccess()
        updateTaskTestSuccess()
    }
}

class View {
    val taskServiceImplementation = TaskServiceImplementation()

    fun input(info: String): String? {
        print("$info : ")
        return readLine()
    }

    fun viewCreateTask() {
        val taskName  = input("Nama task? ")
        val timeStart = input("Mulai pada jam berapa? contoh -> 13:00")
        val timeEnd   = input("Berakhir pada jam berapa? contoh -> 18:00")
        val validate = validator(taskName, timeStart, timeEnd)
        if (!validate) {
            viewCreateTask()
        }else {
            taskServiceImplementation.createTask(TaskCreateRequest(taskName!!, timeStart!!, timeEnd!!))
            println("sukses ditambahkan")
            menu()
        }
    }

    private fun validator(taskName: String?, timeStart: String?, timeEnd: String?): Boolean {
        val taskNameValid = taskName != null && taskName.length < 50 && !taskName.isBlank()
        val timeValid = timeStart!!.contains(":")
        return taskNameValid && timeValid
    }

    fun menu() {
        viewCreateTask()
    }
}

fun main() {
    View().menu()
}