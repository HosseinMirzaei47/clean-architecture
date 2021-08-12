package com.speaking.partner.data.di.settings

import com.speaking.partner.data.datesources.settings.SettingsLocalDataSource
import com.speaking.partner.data.datesources.settings.SettingsLocalDataSourceImpl
import com.speaking.partner.data.repositories.settings.SettingsRepositoryImpl
import com.speaking.partner.domain.repositories.settings.SettingsRepository
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