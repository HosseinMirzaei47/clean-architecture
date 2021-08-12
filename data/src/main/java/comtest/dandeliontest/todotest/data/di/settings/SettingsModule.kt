package comtest.dandeliontest.todotest.data.di.settings

import comtest.dandeliontest.todotest.data.datesources.settings.SettingsLocalDataSource
import comtest.dandeliontest.todotest.data.datesources.settings.SettingsLocalDataSourceImpl
import comtest.dandeliontest.todotest.data.repositories.settings.SettingsRepositoryImpl
import comtest.dandeliontest.todotest.domain.repositories.settings.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    @Singleton
    abstract fun bindSettingsDataSource(impl: SettingsLocalDataSourceImpl): SettingsLocalDataSource

    @Binds
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

}