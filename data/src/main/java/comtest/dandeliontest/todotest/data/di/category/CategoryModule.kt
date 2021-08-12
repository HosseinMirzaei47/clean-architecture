package comtest.dandeliontest.todotest.data.di.category

import comtest.dandeliontest.todotest.data.database.TodoRoomDatabase
import comtest.dandeliontest.todotest.data.datesources.category.CategoryLocalDataSource
import comtest.dandeliontest.todotest.data.datesources.category.CategoryLocalDataSourceImpl
import comtest.dandeliontest.todotest.data.repositories.category.CategoryRepositoryImpl
import comtest.dandeliontest.todotest.domain.repositories.category.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryModule {

    @Binds
    abstract fun bindsCategoryDataSource(impl: CategoryLocalDataSourceImpl): CategoryLocalDataSource

    @Binds
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    companion object {
        @Singleton
        @Provides
        fun provideCategoryDao(
            db: TodoRoomDatabase
        ) = db.categoryDao()
    }
}