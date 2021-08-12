package comtest.dandeliontest.todotest.model_android.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "task_category_cross_ref",
    primaryKeys = ["taskId", "categoryId"],
    indices = [
        Index("taskId", unique = false),
        Index("categoryId", unique = false)
    ]
)
data class TaskCategoryCrossRef(
    val taskId: Long,
    val categoryId: Long,
)