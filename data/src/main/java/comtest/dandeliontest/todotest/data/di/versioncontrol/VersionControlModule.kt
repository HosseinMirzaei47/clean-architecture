package comtest.dandeliontest.todotest.data.di.versioncontrol

import comtest.dandeliontest.todotest.data.api.VersionApiService
import comtest.dandeliontest.todotest.data.datesources.versioncontrol.VersionControlRemoteDataSource
import comtest.dandeliontest.todotest.data.datesources.versioncontrol.VersionControlRemoteDataSourceImpl
import comtest.dandeliontest.todotest.data.repositories.versioncontrol.VersionControlRepositoryImpl
import comtest.dandeliontest.todotest.domain.repositories.versioncontrol.VersionControlRepository
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