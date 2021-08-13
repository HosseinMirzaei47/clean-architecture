package com.app.core.data.database

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
    abstract fun bindDCoreDatabase(
        db: CoreRoomDatabase
    ): CoreDatabase

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): CoreRoomDatabase {
            return Room.databaseBuilder(context, CoreRoomDatabase::class.java, "sample-db").build()
        }
    }
}