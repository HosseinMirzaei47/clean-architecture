package com.speaking.partner.data.di.versioncontrol

import com.speaking.partner.data.datesources.versioncontrol.ChangeLogLocalDataSource
import com.speaking.partner.data.datesources.versioncontrol.ChangeLogLocalDataSourceImpl
import com.speaking.partner.data.repositories.versioncontrol.ChangeLogRepositoryImpl
import com.speaking.partner.domain.repositories.versioncontrol.ChangeLogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChangeLogModule {

    @Binds
    @Singleton
    abstract fun bindsLocalDatasource(impl: ChangeLogLocalDataSourceImpl): ChangeLogLocalDataSource

    @Binds
    @Singleton
    abstract fun bindsRepository(impl: ChangeLogRepositoryImpl): ChangeLogRepository
}
