package comtest.dandeliontest.todotest.data.datesources.reminder

import comtest.dandeliontest.todotest.model_android.entities.ReminderAndTask
import comtest.dandeliontest.todotest.model_android.entities.ReminderEntity

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