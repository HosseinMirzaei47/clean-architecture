package com.speaking.partner.data.di.task

import com.speaking.partner.data.database.CoreRoomDatabase
import com.speaking.partner.data.datesources.sample.SampleLocalDataSource
import com.speaking.partner.data.datesources.sample.SampleLocalDataSourceImpl
import com.speaking.partner.data.repositories.sample.TaskRepositoryImpl
import com.speaking.partner.domain.repositories.sample.TaskRepository
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