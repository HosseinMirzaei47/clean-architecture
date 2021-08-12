package comtest.dandeliontest.todotest

import android.content.Context
import comtest.dandeliontest.todotest.domain.coroutineUtils.ApplicationScope
import comtest.dandeliontest.todotest.domain.coroutineUtils.DefaultDispatcher
import comtest.dandeliontest.todotest.domain.coroutineUtils.MainDispatcher
import comtest.dandeliontest.todotest.domain.usecases.settings.GetChosenThemeUseCase
import comtest.dandeliontest.todotest.domain.usecases.settings.GetLanguageUseCase
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