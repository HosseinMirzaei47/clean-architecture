package comtest.dandeliontest.todotest.data.repositories.reminder

import comtest.dandeliontest.todotest.data.datesources.reminder.ReminderLocalDataSource
import comtest.dandeliontest.todotest.data.mappers.toReminder
import comtest.dandeliontest.todotest.data.mappers.toReminderEntities
import comtest.dandeliontest.todotest.data.mappers.toReminderEntity
import comtest.dandeliontest.todotest.data.mappers.toReminders
import comtest.dandeliontest.todotest.domain.repositories.reminder.ReminderRepository
import comtest.dandeliontest.todotest.model.models.task.Reminder
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderLocalDataSource: ReminderLocalDataSource
) : ReminderRepository {

    override fun createReminder(reminder: Reminder): Long =
        reminderLocalDataSource.createReminder(reminder.toReminderEntity())

    override fun createReminder(reminderEntities: List<Reminder>) =
        reminderLocalDataSource.createReminder(reminderEntities.toReminderEntities())

    override fun deleteReminder(reminder: Reminder) =
        reminderLocalDataSource.deleteReminder(reminder.toReminderEntity())

    override fun deleteReminder(reminderEntities: List<Reminder>) =
        reminderLocalDataSource.deleteReminder(reminderEntities.toReminderEntities())

    override fun deleteAllRemindersOfTask(id: Long) =
        reminderLocalDataSource.deleteAllRemindersOfTask(id)

    override fun updateReminder(reminder: Reminder) =
        reminderLocalDataSource.updateReminder(reminder.toReminderEntity())

    override fun getAllReminders(): List<Reminder> =
        reminderLocalDataSource.getAllReminders().toReminders()

    override fun getFutureReminders(fromTime: Long, untilTime: Long): List<Reminder> =
        reminderLocalDataSource.getFutureReminders(fromTime, untilTime).toReminders()

    override fun getReminder(id: Long): Reminder =
        reminderLocalDataSource.getReminder(id).toReminder()
}