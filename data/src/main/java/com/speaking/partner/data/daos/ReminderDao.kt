package com.speaking.partner.data.daos

import androidx.room.*
import com.speaking.partner.model_android.entities.ReminderAndTask
import com.speaking.partner.model_android.entities.ReminderEntity

@Dao
interface ReminderDao {
    @Insert
    fun insertReminder(reminderEntity: ReminderEntity): Long

    @Transaction
    @Insert
    fun insertReminder(reminderEntities: List<ReminderEntity>)

    @Delete
    fun deleteReminder(reminderEntity: ReminderEntity)

    @Delete
    fun deleteReminder(reminderEntities: List<ReminderEntity>)

    @Query("DELETE FROM reminders WHERE taskOwnerId = :id")
    fun deleteAllRemindersOfTask(id: Long)

    @Update
    fun updateReminder(reminderEntity: ReminderEntity)

    @Query("SELECT * FROM reminders")
    fun getAllReminders(): List<ReminderEntity>

    @Query("SELECT * FROM reminders WHERE time >= :fromTime AND time <= :untilTime")
    fun getFutureReminders(fromTime: Long, untilTime: Long): List<ReminderEntity>

    @Query("SELECT * FROM reminders WHERE reminderId = :id")
    fun getReminder(id: Long): ReminderEntity

    @Transaction
    @Query("SELECT * FROM reminders WHERE reminderId = :id")
    fun getReminderAndTask(id: Long): ReminderAndTask
}