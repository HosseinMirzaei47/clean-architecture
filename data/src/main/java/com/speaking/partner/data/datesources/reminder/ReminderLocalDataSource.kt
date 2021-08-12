package com.speaking.partner.data.datesources.reminder

import com.speaking.partner.model_android.entities.ReminderAndTask
import com.speaking.partner.model_android.entities.ReminderEntity

interface ReminderLocalDataSource {
    fun createReminder(reminderEntity: ReminderEntity): Long

    fun createReminder(reminderEntities: List<ReminderEntity>)

    fun deleteReminder(reminderEntity: ReminderEntity)

    fun deleteReminder(reminderEntities: List<ReminderEntity>)

    fun deleteAllRemindersOfTask(id: Long)

    fun updateReminder(reminderEntity: ReminderEntity)

    fun getAllReminders(): List<ReminderEntity>

    fun getFutureReminders(fromTime: Long, untilTime: Long): List<ReminderEntity>

    fun getReminder(id: Long): ReminderEntity

    fun getReminderAndTask(id: Long): ReminderAndTask
}