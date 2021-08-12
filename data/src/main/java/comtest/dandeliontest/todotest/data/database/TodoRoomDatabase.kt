package comtest.dandeliontest.todotest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import comtest.dandeliontest.todotest.model_android.entities.CategoryEntity
import comtest.dandeliontest.todotest.model_android.entities.ReminderEntity
import comtest.dandeliontest.todotest.model_android.entities.TaskCategoryCrossRef
import comtest.dandeliontest.todotest.model_android.entities.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        CategoryEntity::class,
        ReminderEntity::class,
        TaskCategoryCrossRef::class,
    ],
    version = 2,
    exportSchema = false
)
abstract class TodoRoomDatabase : RoomDatabase(), TodoDatabase