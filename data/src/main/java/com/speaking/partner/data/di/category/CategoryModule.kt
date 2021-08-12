package com.speaking.partner.data.di.category

import com.speaking.partner.data.database.TodoRoomDatabase
import com.speaking.partner.data.datesources.category.CategoryLocalDataSource
import com.speaking.partner.data.datesources.category.CategoryLocalDataSourceImpl
import com.speaking.partner.data.repositories.category.CategoryRepositoryImpl
import com.speaking.partner.domain.repositories.category.CategoryRepository
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