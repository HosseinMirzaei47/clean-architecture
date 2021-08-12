package com.speaking.partner.data.datesources.task

import androidx.sqlite.db.SupportSQLiteQuery
import com.speaking.partner.data.daos.TaskDao
import com.speaking.partner.model_android.entities.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskLocalDataSourceImpl @Inject constructor(
    private val taskDao: TaskDao,
) : TaskLocalDataSource {
    override fun createTask(task: TaskEntity): Long = taskDao.insertTask(task)

    override fun createTask(taskEntities: List<TaskEntity>) = taskDao.insertTask(taskEntities)

    override fun createTaskCategoryRelations(taskCategoryCrossRef: TaskCategoryCrossRef) =
        taskDao.insertTaskWithCategory(taskCategoryCrossRef)

    override fun deleteTask(taskId: Long) = taskDao.deleteTaskById(taskId)

    override fun deleteTask(taskEntities: List<TaskEntity>) = taskDao.deleteTask(taskEntities)

    override fun deleteAllTasks() = taskDao.deleteAllTasks()

    override fun deleteTaskCategoryRelations(taskId: Long) =
        taskDao.deleteTaskCategoryRelations(taskId)

    override fun deleteTaskCategoryRelations(taskCategoryCrossRef: TaskCategoryCrossRef) =
        taskDao.deleteTaskWithCategory(taskCategoryCrossRef)

    override fun updateTask(taskEntity: TaskEntity) = taskDao.updateTask(taskEntity)

    override fun changeIsDoneTask(taskId: Long, isDone: Boolean, doneDate: Long?) =
        taskDao.changeTaskIsDone(taskId, isDone, doneDate)

    override fun getTask(id: Long) = taskDao.getTask(id)

    override fun getTaskWithRemindersAndCategories(id: Long): Flow<TaskWithRemindersAndCategories> =
        taskDao.getTaskWithRemindersAndCategory(id)

    override fun getRemindersOfTask(taskId: Long): List<ReminderEntity> =
        taskDao.getRemindersOfTask(taskId)

    override fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    override fun getAllTasksWithReminders(): Flow<List<TaskWithReminders>> =
        taskDao.getAllTaskWithReminders()

    override fun getAllTaskWithRemindersOrderByDone(): Flow<List<TaskWithReminders>> =
        taskDao.getAllTaskWithRemindersOrderByDone()

    override fun markTaskAsDone(taskId: Long) = taskDao.markTaskAsDone(taskId)

    override fun filterTasks(query: SupportSQLiteQuery): Flow<List<TaskWithReminders>> =
        taskDao.filterTasks(query)

    override fun getWeekTasks(startWeek: Long, endWeek: Long): Flow<List<TaskStatistics>> =
        taskDao.getWeekTasks(startWeek, endWeek)

    override fun getAllTasksCount(): Flow<Int> = taskDao.getAllTasksCount()

    override fun getAllDoneTasksCount(): Flow<Int> = taskDao.getAllDoneTasksCount()

    override fun getTodayTasksCount(startMillis: Long, endMillis: Long): Flow<Int> =
        taskDao.getTodayTasksCount(startMillis, endMillis)

    override fun getUpcomingTasksCount(fromMillis: Long): Flow<Int> =
        taskDao.getUpcomingTasksCount(fromMillis)

}