package com.app.core

import android.content.Context
import com.app.core.domain.coroutineUtils.ApplicationScope
import com.app.core.domain.coroutineUtils.DefaultDispatcher
import com.app.core.domain.coroutineUtils.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @ApplicationScope
    @Singleton
    @Provides
    fun providesApplicationScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

    @Singleton
    @Provides
    fun provideSettingsHelper(
        @ApplicationContext context: Context,
        @MainDispatcher mainDispatcher: CoroutineDispatcher,
    ) = SettingsHelper(context, mainDispatcher)
}