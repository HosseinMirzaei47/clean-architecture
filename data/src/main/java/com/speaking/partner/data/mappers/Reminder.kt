package com.speaking.partner.data.mappers

import com.speaking.partner.model.models.task.Reminder
import com.speaking.partner.model_android.entities.ReminderEntity
import com.support.multicalendar.DandelionDate
import com.support.multicalendar.models.CalendarType.GREGORIAN
import java.util.*

fun Reminder.toReminderEntity() = ReminderEntity(
    reminderId = id,
    taskOwnerId = taskId,
    time = time.timeInMillis,
    createdAt = createdAt.timeInMillis,
    updatedAt = System.currentTimeMillis(),
)

fun ReminderEntity.toReminder() = Reminder(
    id = reminderId,
    taskId = taskOwnerId,
    time = DandelionDate(time, TimeZone.getDefault(), GREGORIAN),
    createdAt = DandelionDate(createdAt, TimeZone.getDefault(), GREGORIAN),
    updatedAt = DandelionDate(updatedAt, TimeZone.getDefault(), GREGORIAN),
)

fun List<Reminder>.toReminderEntities() = map { it.toReminderEntity() }

fun List<ReminderEntity>.toReminders() = map { it.toReminder() }