package comtest.dandeliontest.todotest.data.daos

import androidx.room.*
import comtest.dandeliontest.todotest.model_android.entities.CategoryEntity
import comtest.dandeliontest.todotest.model_android.entities.CategoryWithTaskCount
import comtest.dandeliontest.todotest.model_android.entities.CategoryWithTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert
    fun insertCategory(categoryEntity: CategoryEntity): Long

    @Insert
    fun insertCategory(categoryEntities: List<CategoryEntity>)

    @Query("DELETE FROM categories WHERE categoryId= :categoryId")
    fun deleteCategory(categoryId: Long)

    @Query("DELETE FROM task_category_cross_ref WHERE categoryId = :categoryId")
    fun deleteCategoryTaskRelations(categoryId: Long)

    @Delete
    fun deleteCategory(categoryEntities: List<CategoryEntity>)

    @Update
    fun updateCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Transaction
    @Query("SELECT *,(SELECT count(*) FROM task_category_cross_ref WHERE task_category_cross_ref.categoryId = categories.categoryId)taskCount FROM categories")
    fun getAllCategoriesWithTaskCount(): Flow<List<CategoryWithTaskCount>>

    @Query("SELECT * FROM categories WHERE categoryId = :id")
    fun getCategory(id: Long): Flow<CategoryEntity>

    @Transaction
    @Query("SELECT * FROM categories WHERE categoryId =:id ")
    fun getCategoryWithTasks(id: Long): List<CategoryWithTasks>
}