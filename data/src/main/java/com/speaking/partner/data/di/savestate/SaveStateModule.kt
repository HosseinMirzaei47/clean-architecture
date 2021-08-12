package com.speaking.partner.data.di.savestate

import com.speaking.partner.data.datesources.savestate.SaveStateLocalDataSource
import com.speaking.partner.data.datesources.savestate.SaveStateLocalDataSourceImpl
import com.speaking.partner.data.repositories.savestate.SaveStateRepositoryImpl
import com.speaking.partner.domain.repositories.savestate.SaveStateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class SaveStateModule {
    @Binds
    @Singleton
    abstract fun bindsSaveStateDataSource(dataSourceImpl: SaveStateLocalDataSourceImpl): SaveStateLocalDataSource

    @Binds
    @Singleton
    abstract fun bindsSaveStateRepository(repoImpl: SaveStateRepositoryImpl): SaveStateRepository
}