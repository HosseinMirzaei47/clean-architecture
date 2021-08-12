package comtest.dandeliontest.todotest.data.di.versioncontrol

import comtest.dandeliontest.todotest.data.datesources.versioncontrol.ChangeLogLocalDataSource
import comtest.dandeliontest.todotest.data.datesources.versioncontrol.ChangeLogLocalDataSourceImpl
import comtest.dandeliontest.todotest.data.repositories.versioncontrol.ChangeLogRepositoryImpl
import comtest.dandeliontest.todotest.domain.repositories.versioncontrol.ChangeLogRepository
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
