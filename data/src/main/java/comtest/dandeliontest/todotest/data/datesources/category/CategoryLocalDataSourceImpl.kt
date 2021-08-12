package comtest.dandeliontest.todotest.data.datesources.category

import comtest.dandeliontest.todotest.data.daos.CategoryDao
import comtest.dandeliontest.todotest.model_android.entities.CategoryEntity
import comtest.dandeliontest.todotest.model_android.entities.CategoryWithTaskCount
import comtest.dandeliontest.todotest.model_android.entities.CategoryWithTasks
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryLocalDataSourceImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryLocalDataSource {

    override fun createCategory(categoryEntity: CategoryEntity): Long =
        categoryDao.insertCategory(categoryEntity)

    override fun createCategory(categoryEntities: List<CategoryEntity>) =
        categoryDao.insertCategory(categoryEntities)

    override fun deleteCategory(categoryId: Long) = categoryDao.deleteCategory(categoryId)

    override fun deleteCategoryTaskRelations(categoryId: Long) =
        categoryDao.deleteCategoryTaskRelations(categoryId)

    override fun deleteCategory(categoryEntities: List<CategoryEntity>) =
        categoryDao.deleteCategory(categoryEntities)

    override fun updateCategory(categoryEntity: CategoryEntity) =
        categoryDao.updateCategory(categoryEntity)

    override fun getAllCategories(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()

    override fun getAllCategoriesWithTaskCount(): Flow<List<CategoryWithTaskCount>> =
        categoryDao.getAllCategoriesWithTaskCount()

    override fun getCategory(id: Long): Flow<CategoryEntity> = categoryDao.getCategory(id)

    override fun getCategoriesWithTasks(id: Long): List<CategoryWithTasks> =
        categoryDao.getCategoryWithTasks(id)
}