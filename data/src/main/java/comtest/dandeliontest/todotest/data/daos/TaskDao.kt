package comtest.dandeliontest.todotest.data.daos

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import comtest.dandeliontest.todotest.model_android.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    fun insertTask(taskEntity: TaskEntity): Long

    @Insert
    fun insertTask(taskEntities: List<TaskEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaskWithCategory(taskCategoryCrossRef: TaskCategoryCrossRef)

    @Delete
    fun deleteTask(taskEntities: List<TaskEntity>)

    @Query("DELETE FROM tasks WHERE taskId = :taskId")
    fun deleteTaskById(taskId: Long)

    @Transaction
    @Delete
    fun deleteTaskWithCategory(taskCategoryCrossRef: TaskCategoryCrossRef)

    @Update
    fun updateTask(taskEntity: TaskEntity)

    @Query("UPDATE tasks SET isDone = :isDone  , doneDate = :doneDate WHERE taskId = :taskId")
    fun changeTaskIsDone(taskId: Long, isDone: Boolean, doneDate: Long?)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE taskId = :id")
    fun getTask(id: Long): TaskEntity

    @Transaction
    @Query("SELECT * FROM tasks WHERE taskId = :id")
    fun getTaskWithRemindersAndCategory(id: Long): Flow<TaskWithRemindersAndCategories>

    @Query("SELECT * FROM reminders WHERE taskOwnerId = :taskId")
    fun getRemindersOfTask(taskId: Long): List<ReminderEntity>

    @Query("DELETE FROM tasks")
    fun deleteAllTasks()

    @Query("DELETE FROM task_category_cross_ref WHERE taskId = :taskId")
    fun deleteTaskCategoryRelations(taskId: Long)

    @Transaction
    @Query("SELECT * FROM tasks")
    fun getAllTaskWithReminders(): Flow<List<TaskWithReminders>>

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY isDone ASC")
    fun getAllTaskWithRemindersOrderByDone(): Flow<List<TaskWithReminders>>

    @Query("UPDATE tasks SET isDone = 1 WHERE taskId = :taskId")
    fun markTaskAsDone(taskId: Long)

    @Transaction
    @RawQuery(observedEntities = [TaskEntity::class])
    fun filterTasks(query: SupportSQLiteQuery): Flow<List<TaskWithReminders>>

    @Transaction
    @Query("SELECT dueDate,isDone,doneDate FROM tasks WHERE dueDate >= :startWeek AND dueDate <= :endWeek or doneDate >= :startWeek AND doneDate <= :endWeek ")
    fun getWeekTasks(startWeek: Long, endWeek: Long): Flow<List<TaskStatistics>>

    @Query("SELECT COUNT(taskId) FROM tasks")
    fun getAllTasksCount(): Flow<Int>

    @Query("SELECT COUNT(taskId) FROM tasks WHERE isDone = 1")
    fun getAllDoneTasksCount(): Flow<Int>

    @Query("SELECT COUNT(taskId) FROM tasks WHERE dueDate >= :startMillis AND dueDate < :endMillis")
    fun getTodayTasksCount(startMillis: Long, endMillis: Long): Flow<Int>

    @Query("SELECT COUNT(dueDate) FROM tasks WHERE dueDate >= :startMillis")
    fun getUpcomingTasksCount(startMillis: Long): Flow<Int>

}