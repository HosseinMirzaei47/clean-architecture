package comtest.dandeliontest.todotest.data.di.reminder

import comtest.dandeliontest.todotest.data.database.TodoRoomDatabase
import comtest.dandeliontest.todotest.data.datesources.reminder.ReminderLocalDataSource
import comtest.dandeliontest.todotest.data.datesources.reminder.ReminderLocalDataSourceImpl
import comtest.dandeliontest.todotest.data.repositories.reminder.ReminderRepositoryImpl
import comtest.dandeliontest.todotest.domain.repositories.reminder.ReminderRepository
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