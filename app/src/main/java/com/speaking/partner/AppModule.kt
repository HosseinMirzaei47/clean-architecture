package com.speaking.partner

import android.content.Context
import com.speaking.partner.domain.coroutineUtils.ApplicationScope
import com.speaking.partner.domain.coroutineUtils.DefaultDispatcher
import com.speaking.partner.domain.coroutineUtils.MainDispatcher
import com.speaking.partner.domain.usecases.settings.GetChosenThemeUseCase
import com.speaking.partner.domain.usecases.settings.GetLanguageUseCase
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
        getChosenThemeUseCase: GetChosenThemeUseCase,
        getLanguageUseCase: GetLanguageUseCase,
        @MainDispatcher mainDispatcher: CoroutineDispatcher,
    ) = SettingsHelper(context, getChosenThemeUseCase, getLanguageUseCase, mainDispatcher)
}