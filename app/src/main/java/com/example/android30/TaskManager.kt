package com.example.android30

data class Task(
    val id: Int,
    var title: String,
    var description: String,
    var isCompleted: Boolean = false
)

class TaskManager {

    private val tasks = mutableListOf<Task>()
    private var nextId = 1

    fun addTask(title: String, description: String): Task {
        val task = Task(
            id = nextId++,
            title = title,
            description = description
        )

        tasks.add(task)
        println("Task added: ${task.title}")

        return task
    }

    fun getAllTasks(): List<Task> {
        return tasks.toList()
    }

    fun getTaskById(id: Int): Task? {
        return tasks.find { it.id == id }
    }

    fun updateTask(
        id: Int,
        newTitle: String,
        newDescription: String
    ): Boolean {

        val task = getTaskById(id) ?: return false

        task.title = newTitle
        task.description = newDescription

        println("Task #$id updated")
        return true
    }

    fun completeTask(id: Int): Boolean {
        val task = getTaskById(id) ?: return false

        task.isCompleted = true

        println("Task #$id completed")
        return true
    }

    fun deleteTask(id: Int): Boolean {
        val removed = tasks.removeIf { it.id == id }

        if (removed) {
            println("Task #$id deleted")
        }

        return removed
    }

    fun getCompletedTasks(): List<Task> {
        return tasks.filter { it.isCompleted }
    }

    fun getPendingTasks(): List<Task> {
        return tasks.filter { !it.isCompleted }
    }

    fun printSummary() {
        println("========== TASK SUMMARY ==========")
        println("Total tasks: ${tasks.size}")
        println("Completed: ${getCompletedTasks().size}")
        println("Pending: ${getPendingTasks().size}")
        println("==================================")
    }
}

fun main() {
    val manager = TaskManager()

    manager.addTask(
        "Learn Kotlin",
        "Study classes, functions, and collections"
    )

    manager.addTask(
        "Build Android App",
        "Create a simple to-do application"
    )

    manager.completeTask(1)

    manager.updateTask(
        2,
        "Build Android App UI",
        "Create screens using Jetpack Compose"
    )

    manager.printSummary()

    println("\nAll Tasks:")

    manager.getAllTasks().forEach { task ->
        println(
            """
            ID: ${task.id}
            Title: ${task.title}
            Description: ${task.description}
            Completed: ${task.isCompleted}
            ----------------------------
            """.trimIndent()
        )
    }
}