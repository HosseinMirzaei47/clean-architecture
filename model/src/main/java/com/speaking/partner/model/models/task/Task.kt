package com.speaking.partner.model.models.task

import com.support.multicalendar.DandelionDate
import com.support.multicalendar.models.CalendarType
import java.util.*

data class Task(
    var id: Long = 0,
    var title: String = "",
    var description: String? = "",
    var dueDate: DandelionDate? = null,
    var dueTime: DandelionDate? = null,
    var isDone: Boolean = false,
    var doneDate: DandelionDate? = null,
    var priority: Int = 0,
    var createdAt: DandelionDate = DandelionDate(
        TimeZone.getDefault(),
        CalendarType.GREGORIAN
    ),
    var updatedAt: DandelionDate = DandelionDate(
        TimeZone.getDefault(),
        CalendarType.GREGORIAN
    ),
    var reminders: MutableList<Reminder> = mutableListOf(),
    val categories: MutableList<Category> = mutableListOf()
)