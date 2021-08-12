package com.speaking.partner.data.database

import com.speaking.partner.data.daos.CategoryDao
import com.speaking.partner.data.daos.TaskDao

interface TodoDatabase {
    fun taskDao(): TaskDao
    fun categoryDao(): CategoryDao
    fun reminderDao(): com.speaking.partner.data.daos.ReminderDao
}
