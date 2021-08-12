package comtest.dandeliontest.todotest.domain.repositories.task

import androidx.sqlite.db.SupportSQLiteQuery
import comtest.dandeliontest.todotest.model.models.task.Reminder
import comtest.dandeliontest.todotest.model.models.task.Task
import comtest.dandeliontest.todotest.model.models.task.TaskIdAndCategoryId
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun createTask(task: Task): Long

    fun createTask(tasks: List<Task>)

    fun createTaskCategoryRelations(taskIdAdnCategoryId: TaskIdAndCategoryId)

    fun deleteTask(taskId: Long)

    fun deleteTask(tasks: List<Task>)

    fun deleteAllTasks()

    fun deleteTaskCategoryRelations(taskId: Long)

    fun deleteTaskCategoryRelations(taskIdAdnCategoryId: TaskIdAndCategoryId)

    fun updateTask(task: Task)

    fun changeIsDoneTask(taskId: Long, isDone: Boolean, doneDate: Long?)

    fun getTask(id: Long): Task

    fun getTaskWithRemindersAndTags(id: Long): Flow<Task>

    fun getRemindersOfTask(taskId: Long): List<Reminder>

    fun getAllTasks(): Flow<List<Task>>

    fun getAllTasksWithReminders(): Flow<List<Task>>

    fun getAllTaskWithRemindersOrderByDone(): Flow<List<Task>>

    fun markTaskAsDone(id: Long)

    fun filterTasks(query: SupportSQLiteQuery): Flow<List<Task>>

    fun getWeekTasks(startWeek: Long, endWeek: Long): Flow<List<Task>>

    fun getAllTasksCount(): Flow<Int>

    fun getAllDoneTasksCount(): Flow<Int>

    fun getTodayTasksCount(startMillis: Long, endMillis: Long): Flow<Int>

    fun getUpcomingTasksCount(fromMillis: Long): Flow<Int>

}