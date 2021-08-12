package comtest.dandeliontest.todotest.data.di.task

import comtest.dandeliontest.todotest.data.database.TodoRoomDatabase
import comtest.dandeliontest.todotest.data.datesources.task.TaskLocalDataSource
import comtest.dandeliontest.todotest.data.datesources.task.TaskLocalDataSourceImpl
import comtest.dandeliontest.todotest.data.repositories.task.TaskRepositoryImpl
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
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