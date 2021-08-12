package com.speaking.partner.data.di.versioncontrol

import com.speaking.partner.data.api.VersionApiService
import com.speaking.partner.data.datesources.versioncontrol.VersionControlRemoteDataSource
import com.speaking.partner.data.datesources.versioncontrol.VersionControlRemoteDataSourceImpl
import com.speaking.partner.data.repositories.versioncontrol.VersionControlRepositoryImpl
import com.speaking.partner.domain.repositories.versioncontrol.VersionControlRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
abstract class VersionControlModule {

    @Binds
    abstract fun bindRemoteDataSource(repository: VersionControlRemoteDataSourceImpl): VersionControlRemoteDataSource

    @Binds
    abstract fun bindsRepository(repository: VersionControlRepositoryImpl): VersionControlRepository

    companion object {

        @Provides
        fun provideApiService(retrofit: Retrofit): VersionApiService {
            return retrofit.create(VersionApiService::class.java)
        }
    }
}