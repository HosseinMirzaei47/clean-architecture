package com.speaking.partner.data.database

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseModule {

    @Binds
    @Singleton
    abstract fun bindTodoDatabase(
        db: TodoRoomDatabase
    ): TodoDatabase

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): TodoRoomDatabase {
            return Room.databaseBuilder(context, TodoRoomDatabase::class.java, "todo-db")
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}