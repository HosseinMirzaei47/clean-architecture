package comtest.dandeliontest.todotest.data.di.savestate

import comtest.dandeliontest.todotest.data.datesources.savestate.SaveStateLocalDataSource
import comtest.dandeliontest.todotest.data.datesources.savestate.SaveStateLocalDataSourceImpl
import comtest.dandeliontest.todotest.data.repositories.savestate.SaveStateRepositoryImpl
import comtest.dandeliontest.todotest.domain.repositories.savestate.SaveStateRepository
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