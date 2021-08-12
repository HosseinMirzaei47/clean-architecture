package com.speaking.partner.notification

import android.content.Context
import androidx.work.WorkManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class WorkerModule {

    @Binds
    abstract fun bindsTaskManager(taskManager: TaskManagerImpl): TaskManager

    companion object {
        @Provides
        fun provideWorkManager(@ApplicationContext context: Context) =
            WorkManager.getInstance(context)
    }
}