package comtest.dandeliontest.todotest.data.database

import comtest.dandeliontest.todotest.data.daos.CategoryDao
import comtest.dandeliontest.todotest.data.daos.TaskDao

interface TodoDatabase {
    fun taskDao(): TaskDao
    fun categoryDao(): CategoryDao
    fun reminderDao(): comtest.dandeliontest.todotest.data.daos.ReminderDao
}
