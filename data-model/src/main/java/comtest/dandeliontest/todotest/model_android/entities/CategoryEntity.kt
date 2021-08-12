package comtest.dandeliontest.todotest.model_android.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val categoryId: Long,
    val title: String,
    val color: String = "#ff0000",
    val createdAt: Long,
    val updatedAt: Long,
)