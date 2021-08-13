package com.app.core.data.di.task

import com.app.core.data.database.CoreRoomDatabase
import com.app.core.data.datesources.sample.SampleLocalDataSource
import com.app.core.data.datesources.sample.SampleLocalDataSourceImpl
import com.app.core.data.repositories.sample.TaskRepositoryImpl
import com.app.core.domain.repositories.sample.TaskRepository
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
    abstract fun bindsTaskDataSource(impl: SampleLocalDataSourceImpl): SampleLocalDataSource

    @Binds
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    companion object {
        @Singleton
        @Provides
        fun provideSampleDao(
            db: CoreRoomDatabase
        ) = db.sampleDao()
    }
}