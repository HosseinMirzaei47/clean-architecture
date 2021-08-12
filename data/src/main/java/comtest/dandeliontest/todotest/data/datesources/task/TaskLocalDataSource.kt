package comtest.dandeliontest.todotest.data.datesources.task

import androidx.sqlite.db.SupportSQLiteQuery
import comtest.dandeliontest.todotest.model_android.entities.*
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {
    fun createTask(task: TaskEntity): Long

    fun createTask(taskEntities: List<TaskEntity>)

    fun createTaskCategoryRelations(taskCategoryCrossRef: TaskCategoryCrossRef)

    fun deleteTask(taskId: Long)

    fun deleteTask(taskEntities: List<TaskEntity>)

    fun deleteAllTasks()

    fun deleteTaskCategoryRelations(taskId: Long)

    fun deleteTaskCategoryRelations(taskCategoryCrossRef: TaskCategoryCrossRef)

    fun updateTask(taskEntity: TaskEntity)

    fun changeIsDoneTask(taskId: Long, isDone: Boolean, doneDate: Long?)

    fun getTask(id: Long): TaskEntity

    fun getTaskWithRemindersAndCategories(id: Long): Flow<TaskWithRemindersAndCategories>

    fun getRemindersOfTask(taskId: Long): List<ReminderEntity>

    fun getAllTasks(): Flow<List<TaskEntity>>

    fun getAllTasksWithReminders(): Flow<List<TaskWithReminders>>

    fun getAllTaskWithRemindersOrderByDone(): Flow<List<TaskWithReminders>>

    fun markTaskAsDone(taskId: Long)

    fun filterTasks(query: SupportSQLiteQuery): Flow<List<TaskWithReminders>>

    fun getWeekTasks(startWeek: Long, endWeek: Long): Flow<List<TaskStatistics>>

    fun getAllTasksCount(): Flow<Int>

    fun getAllDoneTasksCount(): Flow<Int>

    fun getTodayTasksCount(startMillis: Long, endMillis: Long): Flow<Int>

    fun getUpcomingTasksCount(fromMillis: Long): Flow<Int>

}