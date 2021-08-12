package com.speaking.partner.data.di.task

import com.speaking.partner.data.database.TodoRoomDatabase
import com.speaking.partner.data.datesources.task.TaskLocalDataSource
import com.speaking.partner.data.datesources.task.TaskLocalDataSourceImpl
import com.speaking.partner.data.repositories.task.TaskRepositoryImpl
import com.speaking.partner.domain.repositories.task.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskModule {

    @Binds
    abstract fun bindsTaskDataSource(impl: TaskLocalDataSourceImpl): TaskLocalDataSource

    @Binds
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    companion object {
        @Singleton
        @Provides
        fun provideTaskDao(
            db: TodoRoomDatabase
        ) = db.taskDao()
    }
}