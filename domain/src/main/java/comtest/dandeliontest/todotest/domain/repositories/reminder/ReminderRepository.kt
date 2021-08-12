package comtest.dandeliontest.todotest.domain.repositories.reminder

import comtest.dandeliontest.todotest.model.models.task.Reminder

interface ReminderRepository {

    fun createReminder(reminder: Reminder): Long

    fun createReminder(reminderEntities: List<Reminder>)

    fun deleteReminder(reminder: Reminder)

    fun deleteReminder(reminderEntities: List<Reminder>)

    fun deleteAllRemindersOfTask(id: Long)

    fun updateReminder(reminder: Reminder)

    fun getAllReminders(): List<Reminder>

    fun getFutureReminders(fromTime: Long, untilTime: Long): List<Reminder>

    fun getReminder(id: Long): Reminder
}