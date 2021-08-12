package com.speaking.partner.model_android.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Long,
    val title: String,
    val description: String? = "",
    val dueDate: Long? = null,
    val dueTime: Long? = null,
    var isDone: Boolean = false,
    val doneDate: Long? = null,
    val priority: Int = 0,
    val createdAt: Long,
    val updatedAt: Long,
)