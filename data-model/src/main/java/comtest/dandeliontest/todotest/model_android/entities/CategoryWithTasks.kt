package comtest.dandeliontest.todotest.model_android.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CategoryWithTasks(
    @Embedded val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "taskId",
        associateBy = Junction(TaskCategoryCrossRef::class)
    )
    val taskEntity: List<TaskEntity>,
)