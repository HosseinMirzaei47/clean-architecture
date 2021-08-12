package com.speaking.partner.data.di.reminder

import com.speaking.partner.data.database.TodoRoomDatabase
import com.speaking.partner.data.datesources.reminder.ReminderLocalDataSource
import com.speaking.partner.data.datesources.reminder.ReminderLocalDataSourceImpl
import com.speaking.partner.data.repositories.reminder.ReminderRepositoryImpl
import com.speaking.partner.domain.repositories.reminder.ReminderRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReminderModule {

    @Binds
    abstract fun bindsReminderDataSource(impl: ReminderLocalDataSourceImpl): ReminderLocalDataSource

    @Binds
    abstract fun bindReminderRepository(impl: ReminderRepositoryImpl): ReminderRepository

    companion object {
        @Singleton
        @Provides
        fun provideReminderDao(
            db: TodoRoomDatabase
        ) = db.reminderDao()
    }
}