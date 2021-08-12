package com.speaking.partner.data.datesources.reminder

import com.speaking.partner.model_android.entities.ReminderAndTask
import com.speaking.partner.model_android.entities.ReminderEntity
import javax.inject.Inject

class ReminderLocalDataSourceImpl @Inject constructor(
    private val reminderDao: com.speaking.partner.data.daos.ReminderDao
) : ReminderLocalDataSource {
    override fun createReminder(reminderEntity: ReminderEntity): Long =
        reminderDao.insertReminder(reminderEntity)

    override fun createReminder(reminderEntities: List<ReminderEntity>) =
        reminderDao.insertReminder(reminderEntities)

    override fun deleteReminder(reminderEntity: ReminderEntity) =
        reminderDao.deleteReminder(reminderEntity)

    override fun deleteReminder(reminderEntities: List<ReminderEntity>) =
        reminderDao.deleteReminder(reminderEntities)

    override fun deleteAllRemindersOfTask(id: Long) = reminderDao.deleteAllRemindersOfTask(id)

    override fun updateReminder(reminderEntity: ReminderEntity) =
        reminderDao.updateReminder(reminderEntity)

    override fun getAllReminders(): List<ReminderEntity> = reminderDao.getAllReminders()

    override fun getFutureReminders(fromTime: Long, untilTime: Long): List<ReminderEntity> =
        reminderDao.getFutureReminders(fromTime, untilTime)

    override fun getReminder(id: Long): ReminderEntity = reminderDao.getReminder(id)

    override fun getReminderAndTask(id: Long): ReminderAndTask = reminderDao.getReminderAndTask(id)
}