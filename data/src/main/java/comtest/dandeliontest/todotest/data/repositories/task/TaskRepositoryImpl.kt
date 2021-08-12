package comtest.dandeliontest.todotest.data.repositories.task

import androidx.sqlite.db.SupportSQLiteQuery
import comtest.dandeliontest.todotest.data.datesources.task.TaskLocalDataSource
import comtest.dandeliontest.todotest.data.mappers.*
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
import comtest.dandeliontest.todotest.model.models.task.Reminder
import comtest.dandeliontest.todotest.model.models.task.Task
import comtest.dandeliontest.todotest.model.models.task.TaskIdAndCategoryId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource,
) : TaskRepository {

    override fun createTask(task: Task): Long = taskLocalDataSource.createTask(task.toTaskEntity())

    override fun createTask(tasks: List<Task>) =
        taskLocalDataSource.createTask(tasks.toTaskEntities())

    override fun createTaskCategoryRelations(taskIdAdnCategoryId: TaskIdAndCategoryId) =
        taskLocalDataSource.createTaskCategoryRelations(taskIdAdnCategoryId.toTaskCategoryCrossRef())

    override fun deleteTask(taskId: Long) = taskLocalDataSource.deleteTask(taskId)

    override fun deleteTask(tasks: List<Task>) =
        taskLocalDataSource.deleteTask(tasks.toTaskEntities())

    override fun deleteAllTasks() = taskLocalDataSource.deleteAllTasks()

    override fun deleteTaskCategoryRelations(taskId: Long) =
        taskLocalDataSource.deleteTaskCategoryRelations(taskId)

    override fun deleteTaskCategoryRelations(taskIdAdnCategoryId: TaskIdAndCategoryId) =
        taskLocalDataSource.deleteTaskCategoryRelations(taskIdAdnCategoryId.toTaskCategoryCrossRef())

    override fun updateTask(task: Task) = taskLocalDataSource.updateTask(task.toTaskEntity())

    override fun changeIsDoneTask(taskId: Long, isDone: Boolean, doneDate: Long?) =
        taskLocalDataSource.changeIsDoneTask(taskId, isDone, doneDate)

    override fun getTask(id: Long): Task = taskLocalDataSource.getTask(id).toTask()

    override fun getTaskWithRemindersAndTags(id: Long): Flow<Task> =
        taskLocalDataSource.getTaskWithRemindersAndCategories(id).map { it.toTask() }

    override fun getRemindersOfTask(taskId: Long): List<Reminder> =
        taskLocalDataSource.getRemindersOfTask(taskId).map { it.toReminder() }

    override fun getAllTasks(): Flow<List<Task>> =
        taskLocalDataSource.getAllTasks().map { it.map { taskEntity -> taskEntity.toTask() } }

    override fun getAllTasksWithReminders(): Flow<List<Task>> =
        taskLocalDataSource.getAllTasksWithReminders().map { it.toTasks() }

    override fun getAllTaskWithRemindersOrderByDone(): Flow<List<Task>> =
        taskLocalDataSource.getAllTaskWithRemindersOrderByDone().map { it.toTasks() }

    override fun markTaskAsDone(id: Long) = taskLocalDataSource.markTaskAsDone(id)

    override fun filterTasks(query: SupportSQLiteQuery): Flow<List<Task>> =
        taskLocalDataSource.filterTasks(query).map { it.toTasks() }

    override fun getWeekTasks(startWeek: Long, endWeek: Long): Flow<List<Task>> =
        taskLocalDataSource.getWeekTasks(startWeek, endWeek)
            .map { it.map { task -> task.toTask() } }

    override fun getAllTasksCount(): Flow<Int> = taskLocalDataSource.getAllTasksCount()

    override fun getAllDoneTasksCount(): Flow<Int> = taskLocalDataSource.getAllDoneTasksCount()

    override fun getTodayTasksCount(startMillis: Long, endMillis: Long): Flow<Int> =
        taskLocalDataSource.getTodayTasksCount(startMillis, endMillis)

    override fun getUpcomingTasksCount(fromMillis: Long): Flow<Int> =
        taskLocalDataSource.getUpcomingTasksCount(fromMillis)
}


